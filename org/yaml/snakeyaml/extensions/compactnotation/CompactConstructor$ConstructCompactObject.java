package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.constructor.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public class ConstructCompactObject extends ConstructMapping
{
    final /* synthetic */ CompactConstructor this$0;
    
    public ConstructCompactObject(final CompactConstructor this$0) {
        this.this$0 = this$0;
        this$0.super();
    }
    
    @Override
    public void construct2ndStep(final Node node, final Object object) {
        final MappingNode mnode = (MappingNode)node;
        final NodeTuple nodeTuple = mnode.getValue().iterator().next();
        final Node valueNode = nodeTuple.getValueNode();
        if (valueNode instanceof MappingNode) {
            valueNode.setType(object.getClass());
            this.constructJavaBean2ndStep((MappingNode)valueNode, object);
        }
        else {
            this.this$0.applySequence(object, CompactConstructor.access$000(this.this$0, (SequenceNode)valueNode));
        }
    }
    
    @Override
    public Object construct(final Node node) {
        ScalarNode tmpNode = null;
        if (node instanceof MappingNode) {
            final MappingNode mnode = (MappingNode)node;
            final NodeTuple nodeTuple = mnode.getValue().iterator().next();
            node.setTwoStepsConstruction(true);
            tmpNode = (ScalarNode)nodeTuple.getKeyNode();
        }
        else {
            tmpNode = (ScalarNode)node;
        }
        final CompactData data = this.this$0.getCompactData(tmpNode.getValue());
        if (data == null) {
            return CompactConstructor.access$100(this.this$0, tmpNode);
        }
        return this.this$0.constructCompactFormat(tmpNode, data);
    }
}
