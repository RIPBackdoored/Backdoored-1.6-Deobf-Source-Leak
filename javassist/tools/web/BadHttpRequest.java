package javassist.tools.web;

public class BadHttpRequest extends Exception
{
    private Exception e;
    
    public BadHttpRequest() {
        super();
        this.e = null;
    }
    
    public BadHttpRequest(final Exception _e) {
        super();
        this.e = _e;
    }
    
    @Override
    public String toString() {
        if (this.e == null) {
            return super.toString();
        }
        return this.e.toString();
    }
}
