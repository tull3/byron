/*
 * Copyright 2017 William Jackson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package digital.tull.project.byron;
 
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import digital.tull.project.byron.logic.Menus;
import digital.tull.project.byron.logic.Session;
import digital.tull.project.byron.transaction.TransactionType;
 
public class App 
{
    public static void main (String[] args)
    {
        Menus.Welcome();
        
        Options options = new Options();
        
        options.addOption("h", "help", false, "Get this menu.");
        options.addOption("cr", "create-record", false, "Create new record.");
        options.addOption("mr", "modify-record", true, "Modify existing record.");
        options.addOption("dr", "delete-record", true, "Delete existing record.");
        options.addOption("lr", "list-records", false, "List existing records.");
        options.addOption("t", "table", true, "Which table to work with.");
        
        CommandLineParser parser = new DefaultParser();
    	
    	CommandLine cmd = null;
    	
		try
		{
			cmd = parser.parse(options, args);
		}
		
		catch (ParseException e)
		{
			System.out.println(e.toString());
		}
		
		TransactionType[] transactions = TransactionType.values();
		
		if (cmd.hasOption("h"))
		{
			HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp("byron", options);
	        System.exit(0);
		}
		
		if (cmd.hasOption("cr"))
		{
			if (!cmd.hasOption("t"))
			{
				System.out.println("Table must be specified with 't=<table_name>'.");
				System.exit(0);
			}
			
			new Session(cmd.getOptionValue("t").toUpperCase(), null, transactions[0]).startSession().runSession();
		}
			
		else if (cmd.hasOption("mr"))
		{
			if (!cmd.hasOption("t"))
			{
				System.out.println("Table must be specified with 't=<table_name>'.");
				System.exit(0);
			}
			
			new Session(cmd.getOptionValue("t").toUpperCase(), cmd.getOptionValue("mr"), transactions[1]).startSession().runSession();
		}
		
		else if (cmd.hasOption("dr"))
		{
			if (!cmd.hasOption("t"))
			{
				System.out.println("Table must be specified with 't=<table_name>'.");
				System.exit(0);
			}
			
			new Session(cmd.getOptionValue("t").toUpperCase(), cmd.getOptionValue("dr"), transactions[2]).startSession().runSession();
		}
		
		else if (cmd.hasOption("lr"))
		{
			if (!cmd.hasOption("t"))
			{
				System.out.println("Table must be specified with 't=<table_name>'.");
				System.exit(0);
			}
			
			new Session(cmd.getOptionValue("t").toUpperCase()).startSession().printEntityList();
		}
    }
}
