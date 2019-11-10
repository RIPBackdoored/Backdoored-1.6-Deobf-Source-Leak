package javassist;

final class ClassPathList
{
    ClassPathList next;
    ClassPath path;
    
    ClassPathList(final ClassPath p, final ClassPathList n) {
        super();
        this.next = n;
        this.path = p;
    }
}
