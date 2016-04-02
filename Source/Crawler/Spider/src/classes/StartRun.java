package classes;

public class StartRun {

	public static void main(String[] args) {
		
		System.out.println("Initilizing Program...\n");
		
		//Taking arguments for the source of seeds and output file
		String seeds = null;
		int maxdepth = 0;
		
		//Checking if arguments are empty and generating exception
		try{
			if(args[0].equals(null)||args[1].equals(null)) {
				throw new Exception();
			}
			else{
				seeds = args[0].concat("seeds.xml");
				maxdepth = Integer.parseInt(args[1]);
			}
		}catch(Exception e){
			System.out.println("Error: Please provide two arguments. Location of seeds.xml and maximum depth...");
			return;
		}
				
		//Checking if the location provided are correct or not
		if(LocationCheck.locationCheck(seeds)) {
			System.out.println("Arguments checked...");
		}
		else return;	
		
		//Passing arguments to next class for further action
		UpdateSeeds.updateSeeds(seeds);
		System.out.println("Seeds updated in Database...");		
		
		//Creating List of all pages in the seeds upto a depth.
		CrawlPages.readSeed(maxdepth);
		
	}

}
