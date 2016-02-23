/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
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

package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 * Provides an Account Balance and Basic Withdrawal/Deposit Operations
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet{
    
    Account instance = new Account();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        try(PrintWriter out = response.getWriter()) {
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
                
            out.println(instance.getBalance());
                        
        } catch (IOException ex) {
            response.setStatus(500);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        String withdrawCash = request.getParameter("withdraw");
        String depositCash = request.getParameter("deposit");
        String closeAcct = request.getParameter("close");
        
        try(PrintWriter out = response.getWriter()) {
            if(withdrawCash != null)
                instance.withdraw(Double.parseDouble(withdrawCash));
            if(depositCash != null)
                instance.deposit(Double.parseDouble(depositCash));
            if(Boolean.parseBoolean(closeAcct))
                instance.close();
            
            out.println(instance.getBalance());
            
        } catch (IOException ex) {
            response.setStatus(500);
        }
        
    }
}
