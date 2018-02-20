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

import java.util.ArrayList;
import java.util.List;

import digital.tull.project.byron.builder.Schema;



public class DDLTransaction implements Transaction
{
    private TransactionType transactionType;
    private Schema schema;
    
    public DDLTransaction(TransactionType transactionType, Schema schema)
    {
        this.transactionType = transactionType;
        this.schema = schema;
    }
    
    @Override
    public List<Object> build()
    {
        List<Object> transactionList = new ArrayList<>();
        
        transactionList.add(0, transactionType);
        transactionList.add(1, schema);
        
        return transactionList;
    }
}
