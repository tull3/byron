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



public enum TransactionType
{
    CREATE_ENTITY(0),
    MODIFY_ENTITY(1),
    DELETE_ENTITY(2),
    VIEW_ENTITY(3);
//    CREATE_TABLE(4),
//    ALTER_COLUMN(5),
//    ADD_CONSTRAINT(6);
    
    private final int option;
    
    private TransactionType(int option)
    {
        this.option = option;
    }
}
