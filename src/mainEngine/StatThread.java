package mainEngine;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class StatThread extends Thread{

	private ArrayBlockingQueue<StatRequest> _abq;
	private Random rand = new Random();
	
	boolean _doIHaveCovid = false;		//did you get covid? While symptoms would not emerge now, you were infected
	int _peopleExposedToCovid = 0;		//number of people you  exposed to covid
	int _peopleWhoHadCovid = 0;			//number of people you were in close(-ish) contact to
	int _peopleInContact = 0;            //number of people you were in contact with during the week
	
	public StatThread(ArrayBlockingQueue<StatRequest> abq) {
		_abq = abq;
	}

	private void doStatistics(StatRequest sr) {
		/*
		 * This method should not be called by anyone but the StatThread. When the ArrayBlockingQueue is not empty, this method
		 * will be called to update numbers and redo statistical calculations
		 */
		if(sr._pop>1) {//no need to do this math if you are alone
		double cvrate = sr._covidRate;
		System.out.println("covid rate: "+cvrate);
		double d = sr._density/(double)100;
		System.out.println("density: "+d);
		boolean m = sr._wearingMask;
		//I am going to attempt to make a normal distribution of the number of people at the event to get some random data
		double pmean;
		double peopleAtEvent;
		if(sr._pop>=30) { //if the number of people at the event is greater than 30 I will do this random number picking
			pmean = (double)sr._pop;
			double p_sd = 0.1* (double)sr._pop; //my standard deviation will be an arbitrary 10% of the number of people at the event
			peopleAtEvent = Math.floor(rand.nextGaussian()*p_sd+pmean);   //nextGaussian*standard deviation + mean gets me a value from the normal distribution
		}else { //if not I will stick with the number of people outlined in the event description
			peopleAtEvent=(double)sr._pop;
		}
		System.out.println("people at event: "+peopleAtEvent);
		
		//expected value of the number of people with covid at the event
		//double peopleWithCovid = Math.floor(peopleAtEvent * cvrate);         <======== originally I wanted to use expected value but to add some more randomness, 
																				//			I will try to get a covid positive person by repeatedly pushing for random numbers
		
		double peopleWithCovid =0;
		
		for(int x =0;x<peopleAtEvent;x++) {
			double randomNum = rand.nextDouble()*1000;
			if(randomNum<=(cvrate*1000)) {
				peopleWithCovid+=1.0;
			}
		}
		System.out.println("people with covid: "+peopleWithCovid);
		
		//now that the number of people with covid at the event has been determined, we will attempt to determine if the player contracts covid
		//first lets see how many people we come into contact with by multiplying the number of people at the event by the density factor
		int numContact = (int)Math.ceil(peopleAtEvent * d);
		//now we use that and the number of people who have covid at the event to create a probability of being in contact with someone who has covid
		double probContact = peopleWithCovid/peopleAtEvent;
		//now to figure out the probability of coming into contact with at least 1 person with covid, we calculate the probability of coming into contact with only people who dont have covid and subtract that from 1
		double probGettingCovid = 1 - Math.pow(1-probContact,numContact);
		System.out.println("prob of getting covid: "+probGettingCovid);
		//now for the determination of the player ACTUALLY coming into contact with a covid positive individual
		double randContact = rand.nextInt(100);
		boolean contact = false;
		if(randContact<(probGettingCovid*100)-1) {
			contact=true;//the player came in contact with a covid individual
		}//no else needed here
		
		if(_doIHaveCovid) {//this is done before the contraction math to prevent spreading errors from contracting covid and spreading it the same day
			//we have 2 cases (and these cases have sub cases where-in I am assuming a mask wearing rate of 70%)
			double peopleInfectedToday=0.0;
			if(m) {//player is wearing a mask
				peopleInfectedToday=numContact*.7*.015;//these people were wearing masks
				peopleInfectedToday+=(numContact*.3*.05);//these people were NOT wearing masks
				_peopleExposedToCovid += (int)Math.floor(peopleInfectedToday);//update how many people the player exposed to covid
			}else {//player is NOT wearing a mask
				peopleInfectedToday=numContact*.7*.7;//these people were wearing masks
				peopleInfectedToday+=(numContact*.3*.9);//no one is wearing a mask
				_peopleExposedToCovid += (int)Math.floor(peopleInfectedToday);//update how many people the player exposed to covid
			}
		}
		
		if(contact && !_doIHaveCovid) {//this is redundant if i have covid
			//For simplicity, this scenario will assume that only 1 encounter with a person with covid during a single event can have the opportunity to transmit the disease
			//Now a covid carrier has 2 states, either they are wearing a mask or not. This will be represented by a weighted probability where there is a 70% chance of the individual wearing a mask. This is arbitrarily chosen
			boolean carrierWearingMask = true;
			int cwmrand = rand.nextInt(10);//picks a number between 0 and 9 inclusive
			if(cwmrand>2) { //if the number chosen is 0, 1, or 2 the individual is NOT wearing a mask
				carrierWearingMask=false;
			}

			//Now for the transmission logic. This will take into account both individuals' state of mask wearing. Then it will compute transmission probability based on that.
			double d100Roll = rand.nextDouble()*100; //this random number will be checked against below
			if(m) {
				if(carrierWearingMask) {//player and covid individual wearing a mask
					_doIHaveCovid = d100Roll<=1.5  ? true : false;
				}else {					//player is wearing a mask but the covid individual is NOT
					_doIHaveCovid = d100Roll<=70.0 ? true : false;
				}	
			}else {
				if(carrierWearingMask) {//player has no mask but the covid individual is wearing a mask
					_doIHaveCovid = d100Roll<=5.0  ? true : false;
				}else {					//Neither are wearing a mask
					_doIHaveCovid = d100Roll<=90.0 ? true : false;
				}	
			}
		}//contact if end
		
		_peopleWhoHadCovid+=peopleWithCovid;//iterate some statistics
		_peopleInContact+=numContact;
		
	}//alone check if end
	System.out.println("People Exposed to Covid:"+ _peopleExposedToCovid);	
	System.out.println("My Covid status:" + _doIHaveCovid);
	System.out.println("People Who Had Covid: "+ _peopleWhoHadCovid);
	
	}

	@Override
	public void run() {
		
		while(true) {
			try { //here I try to take a statrequest off the queue for processing
				doStatistics(_abq.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
