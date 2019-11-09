package f.b.q.l;

public class o extends RuntimeException
{
    public o() {
        this("");
    }
    
    public o(final String v) {
        super(v);
        this.setStackTrace(new StackTraceElement[0]);
    }
    
    @Override
    public String toString() {
        return "Go away john";
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
