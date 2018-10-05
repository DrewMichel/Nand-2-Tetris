public class CompilationEngine
{
    // Constructors
    
    // TODO: Decide on passing String paths, Files, or streams
    
    // Creates a new compilation engine with the given input and output.
    // The next routine called must be compileClass()
    public CompilationEngine(/*Input stream/file, Output stream/file */)
    {
        
    }
    
    // Methods
    
    // Compiles a complete class.
    public void compileClass()
    {
        
    }
    
    // Compiles a static declaration or a field declaration.
    public void compileClassVarDec()
    {
        
    }
    
    // Compiles a complete method, function, or constructor.
    public void compileClassSubroutine()
    {
        
    }
    
    // Compiles a (possibly empty) parameter list, not including the
    // enclosing "()".
    public void compileParameterList()
    {
        
    }
    
    // Compiles a var declaration.
    public void compileVarDec()
    {
        
    }
    
    // Compiles a sequence of statements, not including the enclosing "{}".
    public void compileStatements()
    {
        
    }
    
    // Compiles a do statement.
    public void compileDo()
    {
        
    }
    
    // Compiles a let statement.
    public void compileLet()
    {
        
    }
    
    // Compiles a while statement.
    public void compileWhile()
    {
        
    }
    
    // Compiles a return statement.
    public void compileReturn()
    {
        
    }
    
    // Compiles an if statement, possibly with a trailing else clause.
    public void compileIf()
    {
        
    }
    
    // Compiles an expression.
    public void compileExpression()
    {
        
    }
    
    // Compiles a term. This routine is faced with a slight difficulty when
    // trying to decide between some of the alternative parsing rules.
    // Specifically, if the current token is an identifier, the routine must
    // distinguish between a variable, an array entry, and a subroutine call.
    // A single look-ahead token, which may be one of "[", "(", or "." suffices
    // to distinguish between the three possibilities. Any other token is not
    // part of this term and should not be advanced over.
    public void compileTerm()
    {
        
    }
    
    // Compiles a (possibly empty) comma-separated list of expressions.
    public void compileExpressionList()
    {
        
    }
}