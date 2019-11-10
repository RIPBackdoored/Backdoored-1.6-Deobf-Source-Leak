package com.google.api.client.extensions.java6.auth.oauth2;

import java.util.*;

public abstract class AbstractPromptReceiver implements VerificationCodeReceiver
{
    public AbstractPromptReceiver() {
        super();
    }
    
    public String waitForCode() {
        String code;
        do {
            System.out.print("Please enter code: ");
            code = new Scanner(System.in).nextLine();
        } while (code.isEmpty());
        return code;
    }
    
    public void stop() {
    }
}
