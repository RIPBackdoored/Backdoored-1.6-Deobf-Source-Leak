package com.google.cloud.storage;

import com.google.api.services.storage.model.*;
import com.google.cloud.*;
import java.util.*;

class PolicyHelper
{
    static com.google.cloud.Policy convertFromApiPolicy(final Policy apiPolicy) {
        final com.google.cloud.Policy.Builder policyBuilder = com.google.cloud.Policy.newBuilder();
        for (final Policy.Bindings binding : apiPolicy.getBindings()) {
            for (final String member : binding.getMembers()) {
                policyBuilder.addIdentity(Role.of(binding.getRole()), Identity.valueOf(member), new Identity[0]);
            }
        }
        return policyBuilder.setEtag(apiPolicy.getEtag()).build();
    }
    
    static Policy convertToApiPolicy(final com.google.cloud.Policy policy) {
        final List<Policy.Bindings> bindings = new ArrayList<Policy.Bindings>(policy.getBindings().size());
        for (final Map.Entry<Role, Set<Identity>> entry : policy.getBindings().entrySet()) {
            final List<String> members = new ArrayList<String>(entry.getValue().size());
            for (final Identity identity : entry.getValue()) {
                members.add(identity.strValue());
            }
            bindings.add(new Policy.Bindings().setMembers((List)members).setRole(entry.getKey().getValue()));
        }
        return new Policy().setBindings((List)bindings).setEtag(policy.getEtag());
    }
    
    private PolicyHelper() {
        super();
    }
}
