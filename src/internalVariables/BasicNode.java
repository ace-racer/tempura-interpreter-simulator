/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package internalVariables;
import internalVariables.constantNodes.BasicConstant;
import internalVariables.constantNodes.LanguageConstant;
/**
 *
 * @author anurag
 */
/* contains links to other nodes */
/*Other nodes can be constants, operators or other nodes themselves*/
public class BasicNode extends NodeType 
{
    public int childCount;
    public NodeType [] childNodes;

    public BasicNode(NodeType n0,NodeType n1) 
    {
        super.isBasicNode=true;
        this.childCount=2;
        this.childNodes=new NodeType[this.childCount];
        this.childNodes[0]=n0;
        this.childNodes[1]=n1;
    }
    
    public BasicNode(NodeType n0, NodeType n1, NodeType n2)
    {
        super.isBasicNode=true;
        this.childCount=3;
        this.childNodes=new NodeType[this.childCount];
        this.childNodes[0]=n0;
        this.childNodes[1]=n1;
        this.childNodes[2]=n2;
    }
    
    public BasicNode(NodeType n0, NodeType n1, NodeType n2,NodeType n3)
    {
        super.isBasicNode=true;
        this.childCount=4;
        this.childNodes=new NodeType[this.childCount];
        this.childNodes[0]=n0;
        this.childNodes[1]=n1;
        this.childNodes[2]=n2;
        this.childNodes[3]=n3;
    }
    
    public BasicNode(NodeType n0, NodeType n1, NodeType n2,NodeType n3,NodeType n4)
    {
        super.isBasicNode=true;
        this.childCount=5;
        this.childNodes=new NodeType[this.childCount];
        this.childNodes[0]=n0;
        this.childNodes[1]=n1;
        this.childNodes[2]=n2;
        this.childNodes[3]=n3;
        this.childNodes[4]=n4;
    }
    
    //this method will fire an appropriate operation based on the operator and return a NodeType
    @Override
    public NodeType ExecuteOperation()
    {
        //if the first child is an operator
        if(this.childNodes[0].isOperator==true)
        {
            BasicOperator basicOperator=(BasicOperator)this.childNodes[0];            
            if(basicOperator.operands==1)
            {
                UnaryOperator unaryOperator=(UnaryOperator)childNodes[0];
                return unaryOperator.PerformOperation(childNodes[1]);
            }
            else if(basicOperator.operands==2)
            {
                BinaryOperator binaryOperator=(BinaryOperator)childNodes[0];
                return binaryOperator.PerformOperation(childNodes[1], childNodes[2]);
            }
            else if(basicOperator.operands==3)
            {
                TernaryOperator ternaryOperator=(TernaryOperator)childNodes[0];
                return ternaryOperator.PerformOperation(childNodes[1], childNodes[2], childNodes[3]);
            }
        }
        //if not an operator
        else
        {
            return this.childNodes[0].ExecuteOperation();
        }
        return null;
    }
    
    private void PrintChildNodes()
    {
        int i;
        System.out.println("This node has "+this.childCount+" children.");
        for(i=0;i<this.childCount;i++)
        {
            System.out.println(this.childNodes[i]);
        }
    }
    
    @Override
    public StringBuilder GetFormattedOutput(int level)
    {
        StringBuilder returnStr=new StringBuilder();
        String childContents;
        
        for(int j=0;j<level;j++)
         {
             returnStr.append("\t");
         }
        returnStr.append("<CompoundNode>\n");
        
        for(int i=0;i<this.childCount;i++)
        {
            if(null != this.childNodes[i])
            {
               for(int j=0;j<level;j++)
               {
                   returnStr.append("\t");
               }
                
               if(this.childNodes[i].isBasicNode)
               {
                   returnStr.append(((BasicNode)this.childNodes[i]).GetFormattedOutput(level+1));
               }
               else if(this.childNodes[i].isOperator)
               {
                   childContents=((BasicOperator)this.childNodes[i]).GetNodeContents();
                   childContents=this.RemoveSpaces(childContents);
                   returnStr.append("<").append(childContents).append(">  </").append(childContents).append(">\n");
               }
               else if(this.childNodes[i].isIdentifierNode)
               {
                   childContents=((IdentifierNode)this.childNodes[i]).GetNodeContents();
                   childContents=this.RemoveSpaces(childContents);
                   returnStr.append("<").append(childContents).append(">  ").append(((IdentifierNode)this.childNodes[i]).GetIdentifierName()).append("</").append(childContents).append(">\n");
               }
               else if(this.childNodes[i].isConstant)
               {
                   childContents=((BasicConstant)this.childNodes[i]).GetNodeContents();
                   childContents=this.RemoveSpaces(childContents);
                   returnStr.append("<").append(childContents).append(">  ").append(((BasicConstant)this.childNodes[i]).GetValue().toString()).append("</").append(childContents).append(">\n");
               }
               else 
               {
                   childContents=((LanguageConstant)this.childNodes[i]).GetNodeContents();
                   childContents=this.RemoveSpaces(childContents);
                   returnStr.append("<").append(childContents).append(">  </").append(childContents).append(">\n");
               }
            }
        }
        
        for(int j=0;j<level;j++)
         {
             returnStr.append("\t");
         }
        returnStr.append("</CompoundNode>\n");
        
        return returnStr;
    }
    
    @Override
    public String GetNodeContents()
    {
        return "CompoundNode";
    }

    private String RemoveSpaces(String str) 
    {
        int len=str.length();
        char []inputStr=str.toCharArray();

        int i=0;
        String returnStr="";
        while(i<len)
        {
            //if the character at this position is not a white space
           if(inputStr[i]!=' ')
           {
               returnStr+=inputStr[i];
           }
           i++;
        }
        return returnStr;
    }
}
