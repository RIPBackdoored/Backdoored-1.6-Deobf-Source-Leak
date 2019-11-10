package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;
import javassist.*;

public static class TypeVar extends AbsTypeVar
{
    protected ArrayList lowers;
    protected ArrayList usedBy;
    protected ArrayList uppers;
    protected String fixedType;
    private boolean is2WordType;
    private int visited;
    private int smallest;
    private boolean inList;
    private int dimension;
    
    public TypeVar(final TypeData t) {
        super();
        this.visited = 0;
        this.smallest = 0;
        this.inList = false;
        this.dimension = 0;
        this.uppers = null;
        this.lowers = new ArrayList(2);
        this.usedBy = new ArrayList(2);
        this.merge(t);
        this.fixedType = null;
        this.is2WordType = t.is2WordType();
    }
    
    @Override
    public String getName() {
        if (this.fixedType == null) {
            return this.lowers.get(0).getName();
        }
        return this.fixedType;
    }
    
    @Override
    public BasicType isBasicType() {
        if (this.fixedType == null) {
            return this.lowers.get(0).isBasicType();
        }
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return this.fixedType == null && this.is2WordType;
    }
    
    @Override
    public boolean isNullType() {
        return this.fixedType == null && this.lowers.get(0).isNullType();
    }
    
    @Override
    public boolean isUninit() {
        return this.fixedType == null && this.lowers.get(0).isUninit();
    }
    
    @Override
    public void merge(final TypeData t) {
        this.lowers.add(t);
        if (t instanceof TypeVar) {
            ((TypeVar)t).usedBy.add(this);
        }
    }
    
    @Override
    public int getTypeTag() {
        if (this.fixedType == null) {
            return this.lowers.get(0).getTypeTag();
        }
        return super.getTypeTag();
    }
    
    @Override
    public int getTypeData(final ConstPool cp) {
        if (this.fixedType == null) {
            return this.lowers.get(0).getTypeData(cp);
        }
        return super.getTypeData(cp);
    }
    
    @Override
    public void setType(final String typeName, final ClassPool cp) throws BadBytecode {
        if (this.uppers == null) {
            this.uppers = new ArrayList();
        }
        this.uppers.add(typeName);
    }
    
    @Override
    protected TypeVar toTypeVar(final int dim) {
        this.dimension = dim;
        return this;
    }
    
    @Override
    public TypeData getArrayType(final int dim) throws NotFoundException {
        if (dim == 0) {
            return this;
        }
        final BasicType bt = this.isBasicType();
        if (bt != null) {
            return bt.getArrayType(dim);
        }
        if (this.isNullType()) {
            return new NullType();
        }
        return new ClassName(this.getName()).getArrayType(dim);
    }
    
    @Override
    public int dfs(final ArrayList preOrder, int index, final ClassPool cp) throws NotFoundException {
        if (this.visited > 0) {
            return index;
        }
        final int n2 = ++index;
        this.smallest = n2;
        this.visited = n2;
        preOrder.add(this);
        this.inList = true;
        for (int n = this.lowers.size(), i = 0; i < n; ++i) {
            final TypeVar child = this.lowers.get(i).toTypeVar(this.dimension);
            if (child != null) {
                if (child.visited == 0) {
                    index = child.dfs(preOrder, index, cp);
                    if (child.smallest < this.smallest) {
                        this.smallest = child.smallest;
                    }
                }
                else if (child.inList && child.visited < this.smallest) {
                    this.smallest = child.visited;
                }
            }
        }
        if (this.visited == this.smallest) {
            final ArrayList scc = new ArrayList();
            TypeVar cv;
            do {
                cv = preOrder.remove(preOrder.size() - 1);
                cv.inList = false;
                scc.add(cv);
            } while (cv != this);
            this.fixTypes(scc, cp);
        }
        return index;
    }
    
    private void fixTypes(final ArrayList scc, final ClassPool cp) throws NotFoundException {
        final HashSet lowersSet = new HashSet();
        boolean isBasicType = false;
        TypeData kind = null;
        for (int size = scc.size(), i = 0; i < size; ++i) {
            final TypeVar tvar = scc.get(i);
            final ArrayList tds = tvar.lowers;
            for (int size2 = tds.size(), j = 0; j < size2; ++j) {
                final TypeData td = tds.get(j);
                final TypeData d = td.getArrayType(tvar.dimension);
                final BasicType bt = d.isBasicType();
                if (kind == null) {
                    if (bt == null) {
                        isBasicType = false;
                        kind = d;
                        if (d.isUninit()) {
                            break;
                        }
                    }
                    else {
                        isBasicType = true;
                        kind = bt;
                    }
                }
                else if ((bt == null && isBasicType) || (bt != null && kind != bt)) {
                    isBasicType = true;
                    kind = TypeTag.TOP;
                    break;
                }
                if (bt == null && !d.isNullType()) {
                    lowersSet.add(d.getName());
                }
            }
        }
        if (isBasicType) {
            this.is2WordType = kind.is2WordType();
            this.fixTypes1(scc, kind);
        }
        else {
            final String typeName = this.fixTypes2(scc, lowersSet, cp);
            this.fixTypes1(scc, new ClassName(typeName));
        }
    }
    
    private void fixTypes1(final ArrayList scc, final TypeData kind) throws NotFoundException {
        for (int size = scc.size(), i = 0; i < size; ++i) {
            final TypeVar cv = scc.get(i);
            final TypeData kind2 = kind.getArrayType(-cv.dimension);
            if (kind2.isBasicType() == null) {
                cv.fixedType = kind2.getName();
            }
            else {
                cv.lowers.clear();
                cv.lowers.add(kind2);
                cv.is2WordType = kind2.is2WordType();
            }
        }
    }
    
    private String fixTypes2(final ArrayList scc, final HashSet lowersSet, final ClassPool cp) throws NotFoundException {
        final Iterator it = lowersSet.iterator();
        if (lowersSet.size() == 0) {
            return null;
        }
        if (lowersSet.size() == 1) {
            return it.next();
        }
        CtClass cc = cp.get(it.next());
        while (it.hasNext()) {
            cc = TypeData.commonSuperClassEx(cc, cp.get(it.next()));
        }
        if (cc.getSuperclass() == null || isObjectArray(cc)) {
            cc = this.fixByUppers(scc, cp, new HashSet(), cc);
        }
        if (cc.isArray()) {
            return Descriptor.toJvmName(cc);
        }
        return cc.getName();
    }
    
    private static boolean isObjectArray(final CtClass cc) throws NotFoundException {
        return cc.isArray() && cc.getComponentType().getSuperclass() == null;
    }
    
    private CtClass fixByUppers(final ArrayList users, final ClassPool cp, final HashSet visited, CtClass type) throws NotFoundException {
        if (users == null) {
            return type;
        }
        for (int size = users.size(), i = 0; i < size; ++i) {
            final TypeVar t = users.get(i);
            if (!visited.add(t)) {
                return type;
            }
            if (t.uppers != null) {
                for (int s = t.uppers.size(), k = 0; k < s; ++k) {
                    final CtClass cc = cp.get(t.uppers.get(k));
                    if (cc.subtypeOf(type)) {
                        type = cc;
                    }
                }
            }
            type = this.fixByUppers(t.usedBy, cp, visited, type);
        }
        return type;
    }
    
    @Override
    String toString2(final HashSet hash) {
        hash.add(this);
        if (this.lowers.size() > 0) {
            final TypeData e = this.lowers.get(0);
            if (e != null && !hash.contains(e)) {
                return e.toString2(hash);
            }
        }
        return "?";
    }
}
