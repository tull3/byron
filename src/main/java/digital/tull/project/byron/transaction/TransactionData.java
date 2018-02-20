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

package digital.tull.project.byron.transaction;

import java.io.FileWriter;
import java.io.IOException;

import digital.tull.project.byron.session.Session;



public class TransactionData
{
    private String workingRecord;
    private String workingTable;
    private TransactionType transactionType;
    private String pkColumn;
    private Session session;
    
    public TransactionData(Session session)
    {
        if (session.isValidSession())
            this.session = session;
    }

    public String getWorkingRecord()
    {
        return workingRecord;
    }

    public void setWorkingRecord(String workingRecord)
    {
        this.workingRecord = workingRecord;
    }

    public String getWorkingTable()
    {
        return workingTable;
    }

    public void setWorkingTable(String workingTable)
    {
        this.workingTable = workingTable;
    }

    public TransactionType getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getPKColumn()
    {
        return pkColumn;
    }

    public void setPKColumn(String pkColumn)
    {
        this.pkColumn = pkColumn;
    }
    
    public String toString()
    {
        
        
        return session.getUser().getID() + " performed a " + transactionType + " transaction on table " + workingTable + " and record " + workingRecord + ".";
    }
    
    public void log()
    {
        try (FileWriter out = new FileWriter(session.getUser().getID() + ".log", true))
        {
            out.write(toString());
            //out.flush();
        }
        
        catch (IOException e)
        {
            System.err.println(e.toString());
        }
        
        pkColumn = null;
        transactionType = null;
        workingRecord = null;
        workingTable = null;
    }
}
