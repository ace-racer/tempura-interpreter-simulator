//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";




package interpreter;

//#line 2 "interpreter.y"
  import java.io.*;
  import internalVariables.*;
  import internalVariables.constantNodes.*;
  import internalVariables.relationalOperators.*;
  import internalVariables.unaryOperators.*;
  import internalVariables.repetition.*;
  import internalVariables.temporalOperators.*;
 
  import internalVariables.functions.*;
  import internalVariables.listOperators.*;
  import internalVariables.pointerOperators.*; 
  import symbolTable.Type;

//#line 30 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short MINTOKEN=257;
public final static short FORTIMES=258;
public final static short FORIN=259;
public final static short MAKELIST=260;
public final static short SUB=261;
public final static short SUBLIST=262;
public final static short BOOL=263;
public final static short LOC=264;
public final static short STATE=265;
public final static short STATIC=266;
public final static short LIST=267;
public final static short CALL=268;
public final static short iCALL=269;
public final static short iEXISTS=270;
public final static short iPROCESS=271;
public final static short iPREFIX=272;
public final static short iFOR=273;
public final static short iFORTIMES=274;
public final static short iFORIN=275;
public final static short iPROJ=276;
public final static short iSEMI=277;
public final static short iAND=278;
public final static short ENV=279;
public final static short BND=280;
public final static short MEMBLOCK=281;
public final static short iIF=282;
public final static short iSYSTEM=283;
public final static short iLEN=284;
public final static short iOR=285;
public final static short iUNDEFINED=286;
public final static short RUN=287;
public final static short WHILE=288;
public final static short FOR=289;
public final static short IN=290;
public final static short TIMES=291;
public final static short REPEAT=292;
public final static short EXISTS=293;
public final static short FORALL=294;
public final static short TO=295;
public final static short SIZE=296;
public final static short SKIP=297;
public final static short EMPTY=298;
public final static short MORE=299;
public final static short LAMBDA=300;
public final static short RANLEN=301;
public final static short INFINITE=302;
public final static short END=303;
public final static short NAME=304;
public final static short STRING=305;
public final static short FLOAT=306;
public final static short INT=307;
public final static short PROJ=308;
public final static short FIXLIST=309;
public final static short VARLIST=310;
public final static short NPREV=311;
public final static short SHOWMEM=312;
public final static short EXIT=313;
public final static short SHOWTRACE=314;
public final static short SHOWSTATE=315;
public final static short ABORT=316;
public final static short BREAK=317;
public final static short RETURN=318;
public final static short CONTINUE=319;
public final static short LEAP=320;
public final static short STEP=321;
public final static short PASSLIMIT=322;
public final static short VERBOSITY=323;
public final static short PRINTLEVEL=324;
public final static short DEBUGLEVEL=325;
public final static short STATEPRINT=326;
public final static short REDUNDANCY=327;
public final static short CALLBYNAME=328;
public final static short ASSIGNAHEAD=329;
public final static short BREAKISABORT=330;
public final static short HOPCHOP=331;
public final static short PATH=332;
public final static short HELPDIR=333;
public final static short INFILE=334;
public final static short OUTFILE=335;
public final static short TIMESTEP=336;
public final static short RANDSEED=337;
public final static short RRANDSEED=338;
public final static short SRANDSEED=339;
public final static short SRRANDSEED=340;
public final static short MAXRANDLEN=341;
public final static short PRECISION=342;
public final static short RANDOM=343;
public final static short RRANDOM=344;
public final static short SRANDOM=345;
public final static short SRRANDOM=346;
public final static short NIL=347;
public final static short LBRACE=348;
public final static short PROCESS=349;
public final static short LPAREN=350;
public final static short LBRACK=351;
public final static short RBRACE=352;
public final static short RPAREN=353;
public final static short RBRACK=354;
public final static short BAR=355;
public final static short SEMI=356;
public final static short IMPLIES=357;
public final static short OR=358;
public final static short WHERE=359;
public final static short AND=360;
public final static short IF=361;
public final static short THEN=362;
public final static short IFX=363;
public final static short ELSE=364;
public final static short DEFINE=365;
public final static short SET=366;
public final static short FORMAT=367;
public final static short ATANTWO=368;
public final static short DO=369;
public final static short UNTIL=370;
public final static short HISTORY=371;
public final static short HELP=372;
public final static short GETS=373;
public final static short IS=374;
public final static short ASSN=375;
public final static short UASSN=376;
public final static short IASSN=377;
public final static short DEFAULTS=378;
public final static short KEEP=379;
public final static short THE=380;
public final static short LT=381;
public final static short LE=382;
public final static short EQ=383;
public final static short GE=384;
public final static short GT=385;
public final static short NEQ=386;
public final static short DOUBLEEQ=387;
public final static short LEN=388;
public final static short PLUS=389;
public final static short MINUS=390;
public final static short MULT=391;
public final static short DIV=392;
public final static short MOD=393;
public final static short POWER=394;
public final static short PREFIX=395;
public final static short ALWAYS=396;
public final static short SOMETIME=397;
public final static short HALT=398;
public final static short FIN=399;
public final static short INIT=400;
public final static short STB=401;
public final static short MEM=402;
public final static short INPUT=403;
public final static short WINPUT=404;
public final static short OUTPUT=405;
public final static short PEEK=406;
public final static short CHOPSTAR=407;
public final static short NOT=408;
public final static short NEXT=409;
public final static short WNEXT=410;
public final static short PREV=411;
public final static short TYPE=412;
public final static short ASCII=413;
public final static short STRUCT=414;
public final static short LOAD=415;
public final static short SYSTEM=416;
public final static short UMINUS=417;
public final static short UPLUS=418;
public final static short CEIL=419;
public final static short FLOOR=420;
public final static short SQRT=421;
public final static short ITOF=422;
public final static short EXPO=423;
public final static short LOG=424;
public final static short LOGTEN=425;
public final static short SIN=426;
public final static short COS=427;
public final static short TAN=428;
public final static short ASIN=429;
public final static short ACOS=430;
public final static short ATAN=431;
public final static short SINH=432;
public final static short COSH=433;
public final static short TANH=434;
public final static short FABS=435;
public final static short REFER=436;
public final static short DEREF=437;
public final static short COMMA=438;
public final static short MAXTOKEN=439;
public final static short MQS=440;
public final static short IDN=441;
public final static short MDF=442;
public final static short EIF=443;
public final static short H1=444;
public final static short STL=445;
public final static short IPTL=446;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    8,    8,    9,   10,   10,
   10,   11,   11,   11,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,    6,
    6,    5,    5,    3,    3,    3,    4,    4,    7,    7,
};
final static short yylen[] = {                            2,
    1,    3,    2,    3,    3,    4,    6,    3,    4,    6,
    5,    4,    2,    4,    4,    6,    4,    6,    4,    2,
    2,    4,    2,    2,    2,    2,    4,    7,    2,    2,
    4,    2,    2,    4,    6,    4,    6,    3,    3,    3,
    2,    3,    3,    3,    3,    3,    3,    3,    2,    2,
    3,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    6,    3,    3,    3,    3,    3,
    3,    2,    2,    2,    2,    3,    3,    3,    3,    3,
    3,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    6,    6,
    6,    3,    1,    1,    1,    1,    1,    3,    3,    4,
    6,    1,    4,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    0,
    1,    1,    3,    0,    1,    3,    1,    3,    3,    5,
};
final static short yydefred[] = {                         0,
  105,  116,    0,    0,    0,    0,    0,    0,  120,  119,
  121,    0,  122,  123,    0,  118,  138,  137,    0,    0,
    0,    0,  136,  134,  135,  133,  130,  132,  131,  128,
  129,  124,  125,  126,  127,  117,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,  106,  107,    0,
  104,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  161,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  165,    0,    0,
    0,  139,  140,  141,  142,  143,  144,  145,  146,  147,
  148,  149,  150,  151,  152,  153,  154,  155,  156,  157,
  158,  159,    0,    0,    0,    0,    0,    0,    0,   74,
   75,   50,   52,   53,   54,   57,   58,   60,   56,   25,
   24,   23,   26,   13,   41,   62,   63,   64,   29,   30,
   61,   21,   33,   82,   83,   84,   85,   86,   87,   88,
   89,   90,   91,   92,   93,   94,   95,   96,   97,   98,
   72,   73,    0,    3,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    5,    0,    0,    0,  108,  102,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   81,  109,
    0,    0,    0,    0,    0,    0,    0,    0,  163,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  166,  168,    0,    0,    0,    0,   22,    0,    0,    0,
  110,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  170,    0,    0,    0,
  100,  101,   65,   37,    0,    0,   99,  111,    0,
};
final static short yydgoto[] = {                         94,
   95,  126,  127,  128,  111,  112,  107,   97,   98,   99,
  100,  153,  101,
};
final static short yysindex[] = {                       233,
    0,    0,  233,  233,  415,  233, -205, -204,    0,    0,
    0, -327,    0,    0, -322,    0,    0,    0, -318, -316,
 -309, -246,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  233,  233,  233,  597,
  233,  233, -207,  357, -242, -239,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,    0, 3430,    0,    0,    0, -238,
    0, 4291, 3517, -254,  779, 3292, -257, 3557, -337, -190,
    0, -345, -340, -265, -252, -186,  597,  233,  233,  233,
  233, 3604,  866, 3646,  597, 2991, -231,    0, 3692, 3733,
 -347,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -247,  597,  233,  117, -253, 3772, -379,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 3477,    0,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,  233,  233,
  233,  233,  233,  233,  233,  233,  233,  233,   51,  233,
  233,  233, -348, -245,  233,  233, -186, -256,  233,  233,
  233,  233,  233, -303, -211, -209, 3062, 3101, 3156, 3195,
    0,    0, -106, -206,  233,    0,    0,  233, -186,  233,
  233, -203, 3250,  233,    0,  866, 4329, 1072, 1216, 4352,
  117, -253, -253, -253, -253, -253, -253, -379, -379, -379,
 -379, -379, -379, -374, -374, -243, -243, -243,    0,    0,
 3385,  117, 3813, 4291,  233,  233,  117,  117,    0,  117,
 3852,  117, 3893,  117, -221,    0,  233,  233,  233,  233,
    0,    0, 4376, -201, -253, -253,    0,  233,  117,  233,
    0,  233, 3939,  117,  233,  233,  233, 3981, 4027, 4069,
 4115,  233, -228, 4157, 4203,  117,    0,  117,  117,  117,
    0,    0,    0,    0,  117,  233,    0,    0, -253,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0, -334,    0,    0,    0,
    0,    0,    0,    0, -193,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -200,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  926,    0,    0,    0,
    0, 1003,    0, 3338,    0,    0,    0,    0, -266,    0,
    0,    0,    0,    0,    0, -198, -196,    0,    0,    0,
    0,    0, 2970,    0, -196, -332,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -196,    0, 1884, 1545,    0, 1457,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 4247,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -344,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -198,    0,
    0,    0,    0,    0,    0, 2990, 2950, 2880, 2859, 2769,
 1954, 1974, 1994, 2064, 2085, 2105, 2175, 1572, 1649, 1676,
 1753, 1780, 1857, 1321, 1418, 1134, 1176, 1279,    0,    0,
    0, 2195,    0, -262,    0,    0, 2216, 2286,    0, 2306,
    0, 2326,    0, 2396,    0, 1030,    0,    0,    0,    0,
    0,    0, 2839,    0, 2417, 2437,    0,    0, 2507,    0,
    0,    0,    0, 2527,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, 2548,    0, 2618, 2638, 2658,
    0,    0,    0,    0, 2728,    0,    0,    0, 2749,
};
final static short yygindex[] = {                         0,
    0,    1, -109, -102,  -69, -116,   39,    0,    0,    0,
    0,    0,    0,
};
final static int YYTABLESIZE=4770;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        245,
   96,  117,  259,  102,  103,  106,  108,  246,  162,  223,
  224,  225,  226,  227,  228,  254,  225,  226,  227,  228,
  167,  167,  116,  239,  162,  240,  162,  117,  241,  260,
  242,  118,  295,  119,  160,  231,  160,  122,  123,  124,
  120,  129,  130,  232,  262,  113,  115,  156,  157,  158,
  159,  160,  161,  162,  163,  164,  165,  166,  167,  168,
  169,  170,  171,  172,  173,  174,  175,  176,  177,  178,
  179,  180,  181,  182,  183,  184,  185,  186,  187,  188,
  189,  190,  191,  192,  193,  194,  195,  196,  197,  198,
  199,  200,  201,  202,  203,  117,  131,  115,  109,  114,
  237,  115,  162,  121,  162,  124,  169,  154,  169,  115,
  155,  235,  229,  238,  115,  232,  243,  244,  247,  248,
  249,  250,  256,  296,  295,  253,  232,  217,  218,  261,
  219,  220,  221,  222,  237,  223,  224,  225,  226,  227,
  228,  305,  314,  306,  110,  110,  311,  327,  346,  317,
  228,  333,  312,  164,  160,  263,  164,  112,  115,  115,
  115,  115,  115,  115,  115,  115,  115,  299,  115,    0,
  115,    0,    0,    0,    0,  115,  115,  115,    0,  115,
  115,  115,  115,  115,  115,    0,    0,  115,  115,    0,
  115,  115,  115,  115,    0,  115,  115,  115,  115,  115,
  115,  205,    0,    0,    0,  266,  267,  268,  269,  270,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  286,  287,  288,  289,  291,
  292,  293,  294,    0,    0,  297,  298,    0,    0,  300,
  301,  302,  303,  304,  115,    0,  252,    0,    0,  206,
  207,  208,  209,  210,    0,    0,    0,    0,  313,    0,
  315,  316,    0,    0,  319,    0,  211,  212,  213,  214,
  215,  216,    0,    0,  217,  218,    0,  219,  220,  221,
  222,    0,  223,  224,  225,  226,  227,  228,    0,    0,
    0,    0,    0,    0,    0,  323,  324,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    1,  328,  329,  330,
  331,    0,    0,    2,    0,    0,    0,    0,  334,    0,
  335,    0,  336,    0,    0,  338,  339,  340,    0,    0,
    0,  255,  345,    0,    0,    0,    0,    3,    4,    5,
    0,    0,    6,    7,    8,    0,  349,    9,   10,   11,
   12,   13,   14,    0,   15,   16,   17,   18,    0,   19,
   20,   21,   22,   23,   24,   25,   26,   27,   28,   29,
   30,   31,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   32,   33,   34,   35,   36,   37,   38,
   39,   40,    0,    0,  290,   41,    0,    0,    0,    0,
    0,   42,    0,    0,    0,   43,   44,   45,   46,    0,
    0,    0,   47,    0,    0,    0,    0,    0,    0,   48,
   49,    0,    0,    0,    0,    0,    0,    0,   50,   51,
   52,    0,    0,    0,    0,   53,   54,   55,   56,   57,
   58,   59,   60,   61,   62,   63,   64,   65,   66,   67,
   68,   69,   70,   71,   72,   73,   74,    0,    0,   75,
   76,   77,   78,   79,   80,   81,   82,   83,   84,   85,
   86,   87,   88,   89,   90,   91,   92,   93,    1,  211,
  212,  213,  214,  215,  216,    2,    0,  217,  218,    0,
  219,  220,  221,  222,    0,  223,  224,  225,  226,  227,
  228,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    4,    5,    0,    0,    6,    7,    8,    0,    0,    9,
   10,   11,   12,   13,   14,    0,   15,   16,   17,   18,
    0,   19,   20,   21,   22,   23,   24,   25,   26,   27,
   28,   29,   30,   31,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   32,   33,   34,   35,   36,
   37,   38,   39,   40,    0,    0,    0,   41,    0,    0,
    0,    0,    0,   42,    0,    0,    0,   43,   44,   45,
   46,    0,    0,    0,   47,    0,    0,    0,    0,    0,
    0,   48,   49,    0,    0,    0,    0,    0,    0,    0,
   50,   51,   52,    0,    0,    0,    0,   53,   54,   55,
   56,   57,   58,   59,   60,   61,   62,   63,   64,   65,
   66,   67,   68,   69,   70,   71,   72,   73,   74,    0,
    0,   75,   76,   77,   78,   79,   80,   81,   82,   83,
   84,   85,   86,   87,   88,   89,   90,   91,   92,   93,
    1,    0,    0,    0,    0,    0,    0,    2,  132,  133,
  134,  135,  136,  137,  138,  139,  140,  141,  142,  143,
  144,  145,  146,  147,  148,  149,  150,  151,  152,    0,
    0,    3,    4,    5,    0,    0,    6,    7,    8,    0,
    0,    9,   10,   11,   12,   13,   14,    0,  104,   16,
   17,   18,    0,   19,   20,   21,   22,   23,   24,   25,
   26,   27,   28,   29,   30,   31,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   32,   33,   34,
   35,   36,   37,   38,  105,   40,    0,    0,    0,   41,
    0,    0,    0,    0,    0,   42,    0,    0,    0,   43,
   44,   45,   46,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,   48,   49,    0,    0,    0,    0,    0,
    0,    0,   50,   51,   52,    0,    0,    0,    0,   53,
   54,   55,   56,   57,   58,   59,   60,   61,   62,   63,
   64,   65,   66,   67,   68,   69,   70,   71,   72,   73,
   74,    0,    0,   75,   76,   77,   78,   79,   80,   81,
   82,   83,   84,   85,   86,   87,   88,   89,   90,   91,
   92,   93,    1,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    4,    5,    0,    0,    6,    7,
    8,    0,    0,    9,   10,   11,   12,   13,   14,    0,
   15,   16,   17,   18,    0,   19,   20,   21,   22,   23,
   24,   25,   26,   27,   28,   29,   30,   31,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   32,
   33,   34,   35,   36,   37,   38,  125,   40,    0,    0,
    0,   41,    0,    0,    0,    0,    0,   42,    0,    0,
    0,   43,   44,   45,   46,    0,    0,    0,   47,    0,
    0,    0,    0,    0,    0,   48,   49,    0,    0,    0,
    0,    0,    0,    0,   50,   51,   52,    0,    0,    0,
    0,   53,   54,   55,   56,   57,   58,   59,   60,   61,
   62,   63,   64,   65,   66,   67,   68,   69,   70,   71,
   72,   73,   74,    0,    0,   75,   76,   77,   78,   79,
   80,   81,   82,   83,   84,   85,   86,   87,   88,   89,
   90,   91,   92,   93,    1,    0,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    4,    5,    0,    0,
    6,    7,    8,    0,    0,    9,   10,   11,   12,   13,
   14,    0,  233,   16,   17,   18,    0,   19,   20,   21,
   22,   23,   24,   25,   26,   27,   28,   29,   30,   31,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,   33,   34,   35,   36,   37,   38,   39,   40,
    0,    0,    0,   41,    0,    0,    0,    0,    0,   42,
    0,    0,    0,   43,   44,   45,   46,    0,    0,    0,
   47,    0,    0,    0,    0,    0,    0,   48,   49,    0,
    0,    0,    0,    0,    0,    0,   50,   51,   52,    0,
    0,    0,    0,   53,   54,   55,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,    0,    0,   75,   76,   77,
   78,   79,   80,   81,   82,   83,   84,   85,   86,   87,
   88,   89,   90,   91,   92,   93,  103,    0,    0,    0,
  103,  206,  207,  208,  209,  210,    0,    0,  103,    0,
    0,    0,    0,  103,    0,    0,    0,    0,  211,  212,
  213,  214,  215,  216,    0,    0,  217,  218,    0,  219,
  220,  221,  222,    0,  223,  224,  225,  226,  227,  228,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  114,  103,  103,  103,
  103,  103,  103,  103,  103,  103,    0,  103,    0,  103,
    0,    0,    0,   20,  103,  103,  103,   20,  103,  103,
  103,  103,  103,  103,    0,   20,  103,  103,    0,  103,
  103,  103,  103,    0,  103,  103,  103,  103,  103,  103,
   34,    0,    0,    0,   34,    0,    0,    0,    0,    0,
    0,    0,   34,    0,    0,    0,    0,   34,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   20,   20,   20,   20,    0,    0,
    0,    0,    0,  103,   20,    0,   20,    0,    0,    0,
    0,   20,   20,   20,    0,    0,    0,    0,    0,    0,
  113,   34,   34,   34,   34,   34,   34,   34,   34,   34,
    0,   34,    0,   34,    0,    0,    0,    0,   34,   34,
   34,    0,   34,   34,   34,   34,   34,   34,    0,    0,
   34,   34,    0,   34,   34,   34,   34,    0,   34,   34,
   34,   34,   34,   34,   78,    0,    0,    0,   78,  208,
  209,  210,    0,    0,    0,    0,   78,    0,    0,    0,
   20,   78,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,    0,
  223,  224,  225,  226,  227,  228,   79,   34,    0,    0,
   79,    0,    0,    0,    0,    0,    0,    0,   79,    0,
    0,    0,    0,   79,    0,   78,   78,   78,   78,   78,
   78,   78,   78,   78,    0,   78,    0,   78,    0,    0,
    0,    0,   78,   78,   78,    0,   78,   78,   78,   78,
   78,   78,    0,    0,   78,   78,    0,   78,   78,   78,
   78,    0,   78,   78,   78,   78,   78,   79,   79,   79,
   79,   79,   79,   79,   79,   79,    0,   79,    0,   79,
    0,    0,    0,    0,   79,   79,   79,    0,   79,   79,
   79,   79,   79,   79,    0,    0,   79,   79,    0,   79,
   79,   79,   79,    0,   79,   79,   79,   79,   79,   80,
    0,   78,    0,   80,  209,  210,    0,    0,    0,    0,
    0,   80,    0,    0,    0,    0,   80,    0,  211,  212,
  213,  214,  215,  216,    0,    0,  217,  218,    0,  219,
  220,  221,  222,    0,  223,  224,  225,  226,  227,  228,
    0,   76,    0,   79,    0,   76,    0,    0,    0,    0,
    0,    0,    0,   76,    0,    0,    0,    0,   76,    0,
   80,   80,   80,   80,   80,   80,   80,   80,   80,    0,
   80,    0,   80,    0,    0,    0,    0,   80,   80,   80,
    0,   80,   80,   80,   80,   80,   80,    0,    0,   80,
   80,    0,   80,   80,   80,   80,    0,   80,   80,   80,
   80,   80,   76,   76,   76,   76,   76,   76,   76,   76,
   76,    0,   76,    0,   76,    0,    0,    0,    0,   76,
   76,   76,    0,   76,   76,   76,   76,   76,   76,    0,
    0,   76,   76,    0,   76,   76,   76,   76,   77,   76,
   76,    0,   77,    0,    0,    0,   80,    0,    0,    0,
   77,    0,    0,    0,    0,   77,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,    0,    0,
    0,   55,    0,    0,    0,    0,    0,    0,   76,   55,
    0,    0,    0,    0,   55,    0,    0,    0,    0,   77,
   77,   77,   77,   77,   77,   77,   77,   77,    0,   77,
    0,   77,    0,    0,    0,    0,   77,   77,   77,    0,
   77,   77,   77,   77,   77,   77,    0,    0,   77,   77,
    0,   77,   77,   77,   77,    0,   77,   77,   55,   55,
   55,   55,   55,   55,   55,   55,   55,    0,   55,    0,
   55,    0,    0,    0,    0,   55,   55,   55,    0,   55,
   55,   55,   55,   55,   55,   59,    0,   55,   55,   59,
   55,   55,   55,   55,    0,    0,    0,   59,    0,    0,
    0,    0,   59,    0,    0,   77,    0,    0,    0,    0,
    0,    0,   66,    0,    0,    0,   66,    0,    0,    0,
    0,    0,    0,    0,   66,    0,    0,    0,    0,   66,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   55,    0,   59,   59,   59,   59,
   59,   59,   59,   59,   59,    0,   59,    0,   59,    0,
    0,    0,    0,   59,   59,   59,    0,   59,   59,   59,
   59,   59,   59,   66,   66,   66,   66,   66,   66,   66,
   66,   66,    0,   66,    0,   66,    0,    0,    0,   67,
   66,   66,   66,   67,   66,   66,   66,   66,   66,   66,
    0,   67,    0,    0,    0,    0,   67,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   69,    0,    0,    0,
   69,    0,    0,    0,    0,    0,    0,    0,   69,    0,
    0,    0,   59,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   67,   67,   67,   67,   67,   67,   67,   67,   67,   66,
   67,    0,   67,    0,    0,    0,    0,   67,   67,   67,
    0,   67,   67,   67,   67,   67,   67,   69,   69,   69,
   69,   69,   69,   69,   69,   69,    0,   69,    0,   69,
    0,    0,    0,   70,   69,   69,   69,   70,   69,   69,
   69,   69,   69,   69,    0,   70,    0,    0,    0,    0,
   70,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   71,    0,    0,    0,   71,    0,    0,    0,    0,    0,
    0,    0,   71,    0,    0,    0,   67,   71,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   70,   70,   70,   70,   70,   70,
   70,   70,   70,   69,   70,    0,   70,    0,    0,    0,
    0,   70,   70,   70,    0,   70,   70,   70,   70,   70,
   70,   71,   71,   71,   71,   71,   71,   71,   71,   71,
    0,   71,    0,   71,    0,    0,    0,   68,   71,   71,
   71,   68,   71,   71,   71,   71,   71,   71,    0,   68,
    0,    0,    0,    0,   68,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   32,    0,    0,    0,   32,    0,
    0,    0,    0,    0,    0,    0,   32,    0,    0,    0,
   70,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   68,   68,
   68,   68,   68,   68,   68,   68,   68,   71,   68,    0,
   68,    0,    0,    0,    0,   68,   68,   68,    0,   68,
   68,   68,   68,   68,   68,   32,   32,   32,   32,   32,
   32,   32,   32,   32,   39,   32,    0,   32,   39,    0,
    0,    0,   32,   32,   32,    0,   39,    0,    0,    0,
    0,   39,    0,    0,   43,    0,    0,    0,   43,    0,
    0,    0,    0,    0,    0,    0,   43,    0,    0,    0,
    0,   43,    0,    0,   44,    0,    0,    0,   44,    0,
    0,    0,    0,    0,   68,    0,   44,    0,    0,    0,
    0,   44,    0,    0,    0,   39,   39,   39,   39,   39,
   39,   39,   39,   39,    0,   39,    0,   39,    0,    0,
    0,   32,   39,   39,   39,   43,   43,   43,   43,   43,
   43,   43,   43,   43,    0,   43,    0,   43,    0,    0,
    0,    0,   43,   43,   43,   44,   44,   44,   44,   44,
   44,   44,   44,   44,   45,   44,    0,   44,   45,    0,
    0,    0,   44,   44,   44,    0,   45,    0,    0,    0,
    0,   45,    0,    0,    0,   46,    0,    0,    0,   46,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,   39,   46,    0,    0,   47,    0,    0,    0,   47,
    0,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,   43,   47,    0,    0,   45,   45,   45,   45,   45,
   45,   45,   45,   45,    0,   45,    0,   45,    0,    0,
    0,   44,   45,   45,   45,    0,   46,   46,   46,   46,
   46,   46,   46,   46,   46,    0,   46,    0,   46,    0,
    0,    0,    0,   46,   46,   46,   47,   47,   47,   47,
   47,   47,   47,   47,   47,   48,   47,    0,   47,   48,
    0,    0,    0,   47,   47,   47,    0,   48,    0,    0,
    0,    0,   48,    0,    0,    9,    0,    0,    0,    9,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,   45,    9,    0,    0,    0,   12,    0,    0,    0,
   12,    0,    0,    0,    0,    0,    0,    0,   12,    0,
    0,    0,   46,   12,    0,    0,   48,   48,   48,   48,
   48,   48,   48,   48,   48,    0,   48,    0,   48,    0,
    0,    0,   47,   48,   48,   48,    9,    9,    9,    9,
    9,    9,    9,    9,    9,    0,    9,    0,    9,    0,
    0,    0,    0,    9,    9,    9,    0,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   14,   12,    0,   12,
   14,    0,    0,    0,   12,   12,   12,    0,   14,    0,
    0,    0,    0,   14,    0,    0,   15,    0,    0,    0,
   15,    0,    0,    0,    0,    0,    0,    0,   15,    0,
    0,    0,   48,   15,    0,    0,   17,    0,    0,    0,
   17,    0,    0,    0,    0,    0,    0,    0,   17,    0,
    0,    0,    9,   17,    0,    0,    0,   14,   14,   14,
   14,   14,   14,   14,   14,   14,    0,   14,    0,   14,
    0,    0,    0,   12,   14,   14,   14,   15,   15,   15,
   15,   15,   15,   15,   15,   15,    0,   15,    0,   15,
    0,    0,    0,    0,   15,   15,   15,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   19,   17,    0,   17,
   19,    0,    0,    0,   17,   17,   17,    0,   19,    0,
    0,    0,    0,   19,    0,    0,    0,   27,    0,    0,
    0,   27,    0,    0,    0,    0,    0,    0,    0,   27,
    0,    0,    0,   14,   27,    0,    0,   31,    0,    0,
    0,   31,    0,    0,    0,    0,    0,    0,    0,   31,
    0,    0,    0,   15,   31,    0,    0,   19,   19,   19,
   19,   19,   19,   19,   19,   19,    0,   19,    0,   19,
    0,    0,    0,   17,   19,   19,   19,    0,   27,   27,
   27,   27,   27,   27,   27,   27,   27,    0,   27,    0,
   27,    0,    0,    0,    0,   27,   27,   27,   31,   31,
   31,   31,   31,   31,   31,   31,   31,   36,   31,    0,
   31,   36,    0,    0,    0,   31,   31,   31,    0,   36,
    0,    0,    0,    0,   36,    0,    0,   11,    0,    0,
    0,   11,    0,    0,    0,    0,    0,    0,    0,   11,
    0,    0,    0,   19,   11,    0,    0,    0,   10,    0,
    0,    0,   10,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,   27,   10,    0,    0,   36,   36,
   36,   36,   36,   36,   36,   36,   36,    0,   36,    0,
   36,    0,    0,    0,   31,   36,   36,   36,   11,   11,
   11,   11,   11,   11,   11,   11,   11,    0,   11,    0,
   11,    0,    0,    0,    0,   11,   11,   11,    0,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   16,   10,
    0,   10,   16,    0,    0,    0,   10,   10,   10,    0,
   16,    0,    0,    0,    0,   16,    0,    0,   18,    0,
    0,    0,   18,    0,    0,    0,    0,    0,    0,    0,
   18,    0,    0,    0,   36,   18,    0,    0,   35,    0,
    0,    0,   35,    0,    0,    0,    0,    0,    0,    0,
   35,    0,    0,    0,   11,   35,    0,    0,    0,   16,
   16,   16,   16,   16,   16,   16,   16,   16,    0,   16,
    0,   16,    0,    0,    0,   10,   16,   16,   16,   18,
   18,   18,   18,   18,   18,   18,   18,   18,    0,   18,
    0,   18,    0,    0,    0,    0,   18,   18,   18,   35,
   35,   35,   35,   35,   35,   35,   35,   35,    7,   35,
    0,   35,    7,    0,    0,    0,   35,   35,   35,    0,
    7,    0,    0,    0,    0,    7,    0,    0,    0,   28,
    0,    0,    0,   28,    0,    0,    0,    0,    0,    0,
    0,   28,    0,    0,    0,   16,   28,    0,    0,   40,
    0,    0,    0,   40,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,   18,   40,    0,    0,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    0,    7,
    0,    7,    0,    0,    0,   35,    7,    7,    7,    0,
   28,   28,   28,   28,   28,   28,   28,   28,   28,    0,
   28,    0,   28,    0,    0,    0,    0,   28,   28,   28,
   40,   40,   40,   40,   40,   40,   40,   40,    0,    6,
   40,    0,   40,    6,    0,    0,    0,   40,   40,   40,
    0,    6,    0,    0,    0,    0,    6,    0,    0,   38,
    0,    0,    0,   38,    0,    0,    0,    0,    0,    0,
    0,   38,    0,    0,    0,    7,   38,    0,    0,    0,
    8,    0,    0,    0,    8,    0,    0,    0,    0,    0,
    0,    0,    8,    0,    0,    0,   28,    8,    0,    0,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    0,
    6,    0,    0,    0,    0,    0,   40,    6,    6,    6,
   38,   38,   38,   38,   38,   38,   38,    0,    0,    0,
   38,    0,   38,    0,    0,    0,    0,   38,   38,   38,
    0,    8,    8,    8,    8,    8,    8,    0,    0,    0,
   42,    8,    0,    8,   42,    0,    0,    0,    8,    8,
    8,    0,   42,    0,    0,    0,    0,   42,    0,    0,
   49,    0,    0,    0,   49,    0,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    6,   49,    0,    0,
   51,    0,    0,    0,   51,    0,    0,    0,    0,    0,
    0,    0,   51,    0,    0,    0,   38,   51,  205,    0,
    0,   42,   42,   42,   42,   42,    0,    0,    0,    0,
    0,   42,    0,   42,    0,    0,    0,    8,   42,   42,
   42,   49,   49,   49,   49,    0,    0,    0,    0,    0,
    0,   49,    0,   49,    0,    0,    0,    0,   49,   49,
   49,   51,   51,   51,   51,    0,  206,  207,  208,  209,
  210,   51,    0,   51,    0,    0,    0,    0,   51,   51,
   51,    0,    0,  211,  212,  213,  214,  215,  216,  205,
    0,  217,  218,    0,  219,  220,  221,  222,    0,  223,
  224,  225,  226,  227,  228,    0,    0,   42,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   49,  205,    0,
    0,    0,    0,    0,    0,    0,    0,  206,  207,  208,
  209,  210,    0,    0,    0,    0,    0,   51,  255,    0,
    0,    0,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,    0,
  223,  224,  225,  226,  227,  228,  206,  207,  208,  209,
  210,    0,    0,  205,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  211,  212,  213,  214,  215,  216,    0,
    0,  217,  218,    0,  219,  220,  221,  222,    0,  223,
  224,  225,  226,  227,  228,    0,    0,    0,    0,  307,
    0,    0,  205,    0,    0,    0,    0,    0,    0,    0,
    0,  206,  207,  208,  209,  210,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  211,  212,
  213,  214,  215,  216,    0,    0,  217,  218,  308,  219,
  220,  221,  222,    0,  223,  224,  225,  226,  227,  228,
  206,  207,  208,  209,  210,    0,    0,  205,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  211,  212,  213,
  214,  215,  216,    0,    0,  217,  218,    0,  219,  220,
  221,  222,  234,  223,  224,  225,  226,  227,  228,    0,
    0,    0,    0,  309,    0,    0,    0,    0,    0,  205,
    0,    0,    0,    0,    0,  206,  207,  208,  209,  210,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  211,  212,  213,  214,  215,  216,  115,    0,
  217,  218,  310,  219,  220,  221,  222,    0,  223,  224,
  225,  226,  227,  228,    0,  115,    0,  206,  207,  208,
  209,  210,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,  320,
  223,  224,  225,  226,  227,  228,    0,  318,  112,    0,
    0,    0,  205,  115,  115,  115,  115,  115,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  115,  115,  115,  115,  115,  115,    0,    0,    0,  115,
    0,  115,  115,  115,  115,    0,  115,  115,  115,  115,
  115,  115,  204,    0,    0,    0,    0,  205,  321,    0,
  206,  207,  208,  209,  210,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  211,  212,  213,
  214,  215,  216,    0,    0,  217,  218,    0,  219,  220,
  221,  222,    0,  223,  224,  225,  226,  227,  228,  265,
    0,    0,    0,    0,  205,  206,  207,  208,  209,  210,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  211,  212,  213,  214,  215,  216,    0,    0,
  217,  218,    0,  219,  220,  221,  222,    0,  223,  224,
  225,  226,  227,  228,  205,    0,    0,    0,    0,    0,
    0,    0,  206,  207,  208,  209,  210,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  211,
  212,  213,  214,  215,  216,    0,    0,  217,  218,    0,
  219,  220,  221,  222,  205,  223,  224,  225,  226,  227,
  228,    0,  206,  207,  208,  209,  210,    0,    0,    0,
    0,    0,    0,    0,    0,  230,    0,    0,    0,  211,
  212,  213,  214,  215,  216,    0,    0,  217,  218,    0,
  219,  220,  221,  222,    0,  223,  224,  225,  226,  227,
  228,  205,  206,  207,  208,  209,  210,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  236,    0,    0,  211,
  212,  213,  214,  215,  216,    0,    0,  217,  218,    0,
  219,  220,  221,  222,    0,  223,  224,  225,  226,  227,
  228,    0,    0,  205,    0,  251,    0,    0,    0,  206,
  207,  208,  209,  210,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  211,  212,  213,  214,
  215,  216,    0,    0,  217,  218,    0,  219,  220,  221,
  222,    0,  223,  224,  225,  226,  227,  228,  252,  205,
    0,  206,  207,  208,  209,  210,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  211,  212,
  213,  214,  215,  216,    0,    0,  217,  218,    0,  219,
  220,  221,  222,    0,  223,  224,  225,  226,  227,  228,
  205,    0,    0,    0,    0,    0,  257,  206,  207,  208,
  209,  210,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,  205,
  223,  224,  225,  226,  227,  228,    0,    0,  206,  207,
  208,  209,  210,    0,  258,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  211,  212,  213,  214,  215,
  216,    0,    0,  217,  218,    0,  219,  220,  221,  222,
  205,  223,  224,  225,  226,  227,  228,  206,  207,  208,
  209,  210,    0,    0,    0,    0,    0,    0,    0,    0,
  264,    0,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,  205,
  223,  224,  225,  226,  227,  228,    0,    0,  206,  207,
  208,  209,  210,    0,    0,    0,    0,    0,    0,    0,
    0,  322,    0,    0,    0,  211,  212,  213,  214,  215,
  216,    0,    0,  217,  218,    0,  219,  220,  221,  222,
  205,  223,  224,  225,  226,  227,  228,  206,  207,  208,
  209,  210,    0,    0,    0,    0,    0,    0,    0,    0,
  325,    0,    0,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,    0,
  223,  224,  225,  226,  227,  228,  205,    0,  206,  207,
  208,  209,  210,    0,    0,    0,    0,    0,    0,    0,
    0,  326,    0,    0,    0,  211,  212,  213,  214,  215,
  216,    0,    0,  217,  218,    0,  219,  220,  221,  222,
    0,  223,  224,  225,  226,  227,  228,    0,  205,    0,
    0,  337,    0,    0,  206,  207,  208,  209,  210,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  211,  212,  213,  214,  215,  216,    0,    0,  217,
  218,    0,  219,  220,  221,  222,    0,  223,  224,  225,
  226,  227,  228,  341,  205,    0,  206,  207,  208,  209,
  210,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  211,  212,  213,  214,  215,  216,    0,
    0,  217,  218,    0,  219,  220,  221,  222,    0,  223,
  224,  225,  226,  227,  228,    0,  205,    0,    0,  342,
    0,    0,  206,  207,  208,  209,  210,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  211,
  212,  213,  214,  215,  216,    0,    0,  217,  218,    0,
  219,  220,  221,  222,    0,  223,  224,  225,  226,  227,
  228,  343,  205,    0,  206,  207,  208,  209,  210,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  211,  212,  213,  214,  215,  216,    0,    0,  217,
  218,    0,  219,  220,  221,  222,    0,  223,  224,  225,
  226,  227,  228,    0,  205,    0,    0,  344,    0,    0,
  206,  207,  208,  209,  210,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  211,  212,  213,
  214,  215,  216,    0,    0,  217,  218,    0,  219,  220,
  221,  222,    0,  223,  224,  225,  226,  227,  228,  347,
  205,    0,  206,  207,  208,  209,  210,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  211,
  212,  213,  214,  215,  216,    0,    0,  217,  218,    0,
  219,  220,  221,  222,    0,  223,  224,  225,  226,  227,
  228,    0,    0,    0,  115,    0,  348,    0,  206,  207,
  208,  209,  210,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  211,  212,  213,  214,  215,
  216,    0,    0,  217,  218,    0,  219,  220,  221,  222,
    0,  223,  224,  225,  226,  227,  228,  112,  205,  115,
    0,    0,  115,  115,  115,  115,  115,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  115,
  115,  115,  115,  115,  115,    0,    0,    0,  115,    0,
  115,  115,  115,  115,    0,  115,  115,  115,  115,  115,
  115,    0,    0,    0,    0,    0,  206,  207,  208,  209,
  210,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  211,  212,  213,  214,  215,  216,    0,
    0,  217,  218,    0,  219,  220,  221,  222,    0,  223,
  224,  225,  226,  227,  228,  207,  208,  209,  210,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  211,  212,  213,  214,  215,  216,    0,    0,  217,
  218,  210,  219,  220,  221,  222,    0,  223,  224,  225,
  226,  227,  228,    0,  211,  212,  213,  214,  215,  216,
    0,    0,  217,  218,    0,  219,  220,  221,  222,  332,
  223,  224,  225,  226,  227,  228,    0,    0,  211,  212,
  213,  214,  215,  216,    0,    0,  217,  218,    0,  219,
  220,  221,  222,    0,  223,  224,  225,  226,  227,  228,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        116,
    0,  350,  350,    3,    4,    5,    6,  117,  353,  389,
  390,  391,  392,  393,  394,  125,  391,  392,  393,  394,
  353,  354,  350,  369,  369,  371,  371,  350,  369,  377,
  371,  350,  381,  350,  369,  290,  371,   37,   38,   39,
  350,   41,   42,  381,  154,    7,    8,   47,   48,   49,
   50,   51,   52,   53,   54,   55,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,   76,   77,   78,   79,
   80,   81,   82,   83,   84,   85,   86,   87,   88,   89,
   90,   91,   92,   93,   94,  350,  304,  291,  304,  304,
  438,  295,  369,  350,  371,  105,  369,  350,  371,  303,
  350,  369,  351,  304,  308,  381,  369,  304,  118,  119,
  120,  121,  354,  369,  381,  125,  381,  381,  382,  377,
  384,  385,  386,  387,  438,  389,  390,  391,  392,  393,
  394,  353,  259,  353,  350,  350,  353,  369,  377,  353,
  394,  353,  255,  354,  353,  155,  353,  351,  352,  353,
  354,  355,  356,  357,  358,  359,  360,  237,  362,   -1,
  364,   -1,   -1,   -1,   -1,  369,  370,  371,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,  308,   -1,   -1,   -1,  205,  206,  207,  208,  209,
  210,  211,  212,  213,  214,  215,  216,  217,  218,  219,
  220,  221,  222,  223,  224,  225,  226,  227,  228,  229,
  230,  231,  232,   -1,   -1,  235,  236,   -1,   -1,  239,
  240,  241,  242,  243,  438,   -1,  353,   -1,   -1,  356,
  357,  358,  359,  360,   -1,   -1,   -1,   -1,  258,   -1,
  260,  261,   -1,   -1,  264,   -1,  373,  374,  375,  376,
  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,
  387,   -1,  389,  390,  391,  392,  393,  394,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  295,  296,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  307,  308,  309,
  310,   -1,   -1,  263,   -1,   -1,   -1,   -1,  318,   -1,
  320,   -1,  322,   -1,   -1,  325,  326,  327,   -1,   -1,
   -1,  438,  332,   -1,   -1,   -1,   -1,  287,  288,  289,
   -1,   -1,  292,  293,  294,   -1,  346,  297,  298,  299,
  300,  301,  302,   -1,  304,  305,  306,  307,   -1,  309,
  310,  311,  312,  313,  314,  315,  316,  317,  318,  319,
  320,  321,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  343,  344,  345,  346,  347,  348,  349,
  350,  351,   -1,   -1,  354,  355,   -1,   -1,   -1,   -1,
   -1,  361,   -1,   -1,   -1,  365,  366,  367,  368,   -1,
   -1,   -1,  372,   -1,   -1,   -1,   -1,   -1,   -1,  379,
  380,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  388,  389,
  390,   -1,   -1,   -1,   -1,  395,  396,  397,  398,  399,
  400,  401,  402,  403,  404,  405,  406,  407,  408,  409,
  410,  411,  412,  413,  414,  415,  416,   -1,   -1,  419,
  420,  421,  422,  423,  424,  425,  426,  427,  428,  429,
  430,  431,  432,  433,  434,  435,  436,  437,  256,  373,
  374,  375,  376,  377,  378,  263,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  287,
  288,  289,   -1,   -1,  292,  293,  294,   -1,   -1,  297,
  298,  299,  300,  301,  302,   -1,  304,  305,  306,  307,
   -1,  309,  310,  311,  312,  313,  314,  315,  316,  317,
  318,  319,  320,  321,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  343,  344,  345,  346,  347,
  348,  349,  350,  351,   -1,   -1,   -1,  355,   -1,   -1,
   -1,   -1,   -1,  361,   -1,   -1,   -1,  365,  366,  367,
  368,   -1,   -1,   -1,  372,   -1,   -1,   -1,   -1,   -1,
   -1,  379,  380,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  388,  389,  390,   -1,   -1,   -1,   -1,  395,  396,  397,
  398,  399,  400,  401,  402,  403,  404,  405,  406,  407,
  408,  409,  410,  411,  412,  413,  414,  415,  416,   -1,
   -1,  419,  420,  421,  422,  423,  424,  425,  426,  427,
  428,  429,  430,  431,  432,  433,  434,  435,  436,  437,
  256,   -1,   -1,   -1,   -1,   -1,   -1,  263,  322,  323,
  324,  325,  326,  327,  328,  329,  330,  331,  332,  333,
  334,  335,  336,  337,  338,  339,  340,  341,  342,   -1,
   -1,  287,  288,  289,   -1,   -1,  292,  293,  294,   -1,
   -1,  297,  298,  299,  300,  301,  302,   -1,  304,  305,
  306,  307,   -1,  309,  310,  311,  312,  313,  314,  315,
  316,  317,  318,  319,  320,  321,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  343,  344,  345,
  346,  347,  348,  349,  350,  351,   -1,   -1,   -1,  355,
   -1,   -1,   -1,   -1,   -1,  361,   -1,   -1,   -1,  365,
  366,  367,  368,   -1,   -1,   -1,  372,   -1,   -1,   -1,
   -1,   -1,   -1,  379,  380,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  388,  389,  390,   -1,   -1,   -1,   -1,  395,
  396,  397,  398,  399,  400,  401,  402,  403,  404,  405,
  406,  407,  408,  409,  410,  411,  412,  413,  414,  415,
  416,   -1,   -1,  419,  420,  421,  422,  423,  424,  425,
  426,  427,  428,  429,  430,  431,  432,  433,  434,  435,
  436,  437,  256,   -1,   -1,   -1,   -1,   -1,   -1,  263,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  287,  288,  289,   -1,   -1,  292,  293,
  294,   -1,   -1,  297,  298,  299,  300,  301,  302,   -1,
  304,  305,  306,  307,   -1,  309,  310,  311,  312,  313,
  314,  315,  316,  317,  318,  319,  320,  321,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  343,
  344,  345,  346,  347,  348,  349,  350,  351,   -1,   -1,
   -1,  355,   -1,   -1,   -1,   -1,   -1,  361,   -1,   -1,
   -1,  365,  366,  367,  368,   -1,   -1,   -1,  372,   -1,
   -1,   -1,   -1,   -1,   -1,  379,  380,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  388,  389,  390,   -1,   -1,   -1,
   -1,  395,  396,  397,  398,  399,  400,  401,  402,  403,
  404,  405,  406,  407,  408,  409,  410,  411,  412,  413,
  414,  415,  416,   -1,   -1,  419,  420,  421,  422,  423,
  424,  425,  426,  427,  428,  429,  430,  431,  432,  433,
  434,  435,  436,  437,  256,   -1,   -1,   -1,   -1,   -1,
   -1,  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  287,  288,  289,   -1,   -1,
  292,  293,  294,   -1,   -1,  297,  298,  299,  300,  301,
  302,   -1,  304,  305,  306,  307,   -1,  309,  310,  311,
  312,  313,  314,  315,  316,  317,  318,  319,  320,  321,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  343,  344,  345,  346,  347,  348,  349,  350,  351,
   -1,   -1,   -1,  355,   -1,   -1,   -1,   -1,   -1,  361,
   -1,   -1,   -1,  365,  366,  367,  368,   -1,   -1,   -1,
  372,   -1,   -1,   -1,   -1,   -1,   -1,  379,  380,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  388,  389,  390,   -1,
   -1,   -1,   -1,  395,  396,  397,  398,  399,  400,  401,
  402,  403,  404,  405,  406,  407,  408,  409,  410,  411,
  412,  413,  414,  415,  416,   -1,   -1,  419,  420,  421,
  422,  423,  424,  425,  426,  427,  428,  429,  430,  431,
  432,  433,  434,  435,  436,  437,  291,   -1,   -1,   -1,
  295,  356,  357,  358,  359,  360,   -1,   -1,  303,   -1,
   -1,   -1,   -1,  308,   -1,   -1,   -1,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  351,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,  291,  369,  370,  371,  295,  373,  374,
  375,  376,  377,  378,   -1,  303,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  352,  353,  354,  355,   -1,   -1,
   -1,   -1,   -1,  438,  362,   -1,  364,   -1,   -1,   -1,
   -1,  369,  370,  371,   -1,   -1,   -1,   -1,   -1,   -1,
  351,  352,  353,  354,  355,  356,  357,  358,  359,  360,
   -1,  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,
  371,   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,
  381,  382,   -1,  384,  385,  386,  387,   -1,  389,  390,
  391,  392,  393,  394,  291,   -1,   -1,   -1,  295,  358,
  359,  360,   -1,   -1,   -1,   -1,  303,   -1,   -1,   -1,
  438,  308,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,
  389,  390,  391,  392,  393,  394,  291,  438,   -1,   -1,
  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,
   -1,   -1,   -1,  308,   -1,  352,  353,  354,  355,  356,
  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,   -1,
   -1,   -1,  369,  370,  371,   -1,  373,  374,  375,  376,
  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,
  387,   -1,  389,  390,  391,  392,  393,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,   -1,  369,  370,  371,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  291,
   -1,  438,   -1,  295,  359,  360,   -1,   -1,   -1,   -1,
   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
   -1,  291,   -1,  438,   -1,  295,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,
  352,  353,  354,  355,  356,  357,  358,  359,  360,   -1,
  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,
   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,  381,
  382,   -1,  384,  385,  386,  387,   -1,  389,  390,  391,
  392,  393,  352,  353,  354,  355,  356,  357,  358,  359,
  360,   -1,  362,   -1,  364,   -1,   -1,   -1,   -1,  369,
  370,  371,   -1,  373,  374,  375,  376,  377,  378,   -1,
   -1,  381,  382,   -1,  384,  385,  386,  387,  291,  389,
  390,   -1,  295,   -1,   -1,   -1,  438,   -1,   -1,   -1,
  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,
   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,  438,  303,
   -1,   -1,   -1,   -1,  308,   -1,   -1,   -1,   -1,  352,
  353,  354,  355,  356,  357,  358,  359,  360,   -1,  362,
   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,   -1,
  373,  374,  375,  376,  377,  378,   -1,   -1,  381,  382,
   -1,  384,  385,  386,  387,   -1,  389,  390,  352,  353,
  354,  355,  356,  357,  358,  359,  360,   -1,  362,   -1,
  364,   -1,   -1,   -1,   -1,  369,  370,  371,   -1,  373,
  374,  375,  376,  377,  378,  291,   -1,  381,  382,  295,
  384,  385,  386,  387,   -1,   -1,   -1,  303,   -1,   -1,
   -1,   -1,  308,   -1,   -1,  438,   -1,   -1,   -1,   -1,
   -1,   -1,  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  303,   -1,   -1,   -1,   -1,  308,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  438,   -1,  352,  353,  354,  355,
  356,  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,
   -1,   -1,   -1,  369,  370,  371,   -1,  373,  374,  375,
  376,  377,  378,  352,  353,  354,  355,  356,  357,  358,
  359,  360,   -1,  362,   -1,  364,   -1,   -1,   -1,  291,
  369,  370,  371,  295,  373,  374,  375,  376,  377,  378,
   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  291,   -1,   -1,   -1,
  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,
   -1,   -1,  438,  308,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  352,  353,  354,  355,  356,  357,  358,  359,  360,  438,
  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,
   -1,  373,  374,  375,  376,  377,  378,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,  291,  369,  370,  371,  295,  373,  374,
  375,  376,  377,  378,   -1,  303,   -1,   -1,   -1,   -1,
  308,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  352,  353,  354,  355,  356,  357,
  358,  359,  360,  438,  362,   -1,  364,   -1,   -1,   -1,
   -1,  369,  370,  371,   -1,  373,  374,  375,  376,  377,
  378,  352,  353,  354,  355,  356,  357,  358,  359,  360,
   -1,  362,   -1,  364,   -1,   -1,   -1,  291,  369,  370,
  371,  295,  373,  374,  375,  376,  377,  378,   -1,  303,
   -1,   -1,   -1,   -1,  308,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  291,   -1,   -1,   -1,  295,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,   -1,   -1,
  438,  308,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  352,  353,
  354,  355,  356,  357,  358,  359,  360,  438,  362,   -1,
  364,   -1,   -1,   -1,   -1,  369,  370,  371,   -1,  373,
  374,  375,  376,  377,  378,  352,  353,  354,  355,  356,
  357,  358,  359,  360,  291,  362,   -1,  364,  295,   -1,
   -1,   -1,  369,  370,  371,   -1,  303,   -1,   -1,   -1,
   -1,  308,   -1,   -1,  291,   -1,   -1,   -1,  295,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,   -1,   -1,
   -1,  308,   -1,   -1,  291,   -1,   -1,   -1,  295,   -1,
   -1,   -1,   -1,   -1,  438,   -1,  303,   -1,   -1,   -1,
   -1,  308,   -1,   -1,   -1,  352,  353,  354,  355,  356,
  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,   -1,
   -1,  438,  369,  370,  371,  352,  353,  354,  355,  356,
  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,   -1,
   -1,   -1,  369,  370,  371,  352,  353,  354,  355,  356,
  357,  358,  359,  360,  291,  362,   -1,  364,  295,   -1,
   -1,   -1,  369,  370,  371,   -1,  303,   -1,   -1,   -1,
   -1,  308,   -1,   -1,   -1,  291,   -1,   -1,   -1,  295,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,   -1,
   -1,  438,  308,   -1,   -1,  291,   -1,   -1,   -1,  295,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,   -1,
   -1,  438,  308,   -1,   -1,  352,  353,  354,  355,  356,
  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,   -1,
   -1,  438,  369,  370,  371,   -1,  352,  353,  354,  355,
  356,  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,
   -1,   -1,   -1,  369,  370,  371,  352,  353,  354,  355,
  356,  357,  358,  359,  360,  291,  362,   -1,  364,  295,
   -1,   -1,   -1,  369,  370,  371,   -1,  303,   -1,   -1,
   -1,   -1,  308,   -1,   -1,  291,   -1,   -1,   -1,  295,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,   -1,
   -1,  438,  308,   -1,   -1,   -1,  291,   -1,   -1,   -1,
  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,
   -1,   -1,  438,  308,   -1,   -1,  352,  353,  354,  355,
  356,  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,
   -1,   -1,  438,  369,  370,  371,  352,  353,  354,  355,
  356,  357,  358,  359,  360,   -1,  362,   -1,  364,   -1,
   -1,   -1,   -1,  369,  370,  371,   -1,  352,  353,  354,
  355,  356,  357,  358,  359,  360,  291,  362,   -1,  364,
  295,   -1,   -1,   -1,  369,  370,  371,   -1,  303,   -1,
   -1,   -1,   -1,  308,   -1,   -1,  291,   -1,   -1,   -1,
  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,
   -1,   -1,  438,  308,   -1,   -1,  291,   -1,   -1,   -1,
  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,   -1,
   -1,   -1,  438,  308,   -1,   -1,   -1,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,  438,  369,  370,  371,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,   -1,  369,  370,  371,  352,  353,  354,
  355,  356,  357,  358,  359,  360,  291,  362,   -1,  364,
  295,   -1,   -1,   -1,  369,  370,  371,   -1,  303,   -1,
   -1,   -1,   -1,  308,   -1,   -1,   -1,  291,   -1,   -1,
   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,
   -1,   -1,   -1,  438,  308,   -1,   -1,  291,   -1,   -1,
   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,
   -1,   -1,   -1,  438,  308,   -1,   -1,  352,  353,  354,
  355,  356,  357,  358,  359,  360,   -1,  362,   -1,  364,
   -1,   -1,   -1,  438,  369,  370,  371,   -1,  352,  353,
  354,  355,  356,  357,  358,  359,  360,   -1,  362,   -1,
  364,   -1,   -1,   -1,   -1,  369,  370,  371,  352,  353,
  354,  355,  356,  357,  358,  359,  360,  291,  362,   -1,
  364,  295,   -1,   -1,   -1,  369,  370,  371,   -1,  303,
   -1,   -1,   -1,   -1,  308,   -1,   -1,  291,   -1,   -1,
   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  303,
   -1,   -1,   -1,  438,  308,   -1,   -1,   -1,  291,   -1,
   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  303,   -1,   -1,   -1,  438,  308,   -1,   -1,  352,  353,
  354,  355,  356,  357,  358,  359,  360,   -1,  362,   -1,
  364,   -1,   -1,   -1,  438,  369,  370,  371,  352,  353,
  354,  355,  356,  357,  358,  359,  360,   -1,  362,   -1,
  364,   -1,   -1,   -1,   -1,  369,  370,  371,   -1,  352,
  353,  354,  355,  356,  357,  358,  359,  360,  291,  362,
   -1,  364,  295,   -1,   -1,   -1,  369,  370,  371,   -1,
  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,  291,   -1,
   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  303,   -1,   -1,   -1,  438,  308,   -1,   -1,  291,   -1,
   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  303,   -1,   -1,   -1,  438,  308,   -1,   -1,   -1,  352,
  353,  354,  355,  356,  357,  358,  359,  360,   -1,  362,
   -1,  364,   -1,   -1,   -1,  438,  369,  370,  371,  352,
  353,  354,  355,  356,  357,  358,  359,  360,   -1,  362,
   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,  352,
  353,  354,  355,  356,  357,  358,  359,  360,  291,  362,
   -1,  364,  295,   -1,   -1,   -1,  369,  370,  371,   -1,
  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,   -1,  291,
   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,  291,
   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,  352,
  353,  354,  355,  356,  357,  358,  359,  360,   -1,  362,
   -1,  364,   -1,   -1,   -1,  438,  369,  370,  371,   -1,
  352,  353,  354,  355,  356,  357,  358,  359,  360,   -1,
  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,
  352,  353,  354,  355,  356,  357,  358,  359,   -1,  291,
  362,   -1,  364,  295,   -1,   -1,   -1,  369,  370,  371,
   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,  291,
   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,   -1,
  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,
  352,  353,  354,  355,  356,  357,  358,  359,  360,   -1,
  362,   -1,   -1,   -1,   -1,   -1,  438,  369,  370,  371,
  352,  353,  354,  355,  356,  357,  358,   -1,   -1,   -1,
  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,  371,
   -1,  352,  353,  354,  355,  356,  357,   -1,   -1,   -1,
  291,  362,   -1,  364,  295,   -1,   -1,   -1,  369,  370,
  371,   -1,  303,   -1,   -1,   -1,   -1,  308,   -1,   -1,
  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  303,   -1,   -1,   -1,  438,  308,   -1,   -1,
  291,   -1,   -1,   -1,  295,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  303,   -1,   -1,   -1,  438,  308,  308,   -1,
   -1,  352,  353,  354,  355,  356,   -1,   -1,   -1,   -1,
   -1,  362,   -1,  364,   -1,   -1,   -1,  438,  369,  370,
  371,  352,  353,  354,  355,   -1,   -1,   -1,   -1,   -1,
   -1,  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,
  371,  352,  353,  354,  355,   -1,  356,  357,  358,  359,
  360,  362,   -1,  364,   -1,   -1,   -1,   -1,  369,  370,
  371,   -1,   -1,  373,  374,  375,  376,  377,  378,  308,
   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,  389,
  390,  391,  392,  393,  394,   -1,   -1,  438,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  438,  308,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  356,  357,  358,
  359,  360,   -1,   -1,   -1,   -1,   -1,  438,  438,   -1,
   -1,   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,
  389,  390,  391,  392,  393,  394,  356,  357,  358,  359,
  360,   -1,   -1,  308,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,   -1,
   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,  389,
  390,  391,  392,  393,  394,   -1,   -1,   -1,   -1,  438,
   -1,   -1,  308,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,  438,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
  356,  357,  358,  359,  360,   -1,   -1,  308,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,  375,
  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,
  386,  387,  291,  389,  390,  391,  392,  393,  394,   -1,
   -1,   -1,   -1,  438,   -1,   -1,   -1,   -1,   -1,  308,
   -1,   -1,   -1,   -1,   -1,  356,  357,  358,  359,  360,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  373,  374,  375,  376,  377,  378,  291,   -1,
  381,  382,  438,  384,  385,  386,  387,   -1,  389,  390,
  391,  392,  393,  394,   -1,  308,   -1,  356,  357,  358,
  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,  295,
  389,  390,  391,  392,  393,  394,   -1,  438,  351,   -1,
   -1,   -1,  308,  356,  357,  358,  359,  360,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  373,  374,  375,  376,  377,  378,   -1,   -1,   -1,  382,
   -1,  384,  385,  386,  387,   -1,  389,  390,  391,  392,
  393,  394,  303,   -1,   -1,   -1,   -1,  308,  354,   -1,
  356,  357,  358,  359,  360,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,  375,
  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,
  386,  387,   -1,  389,  390,  391,  392,  393,  394,  303,
   -1,   -1,   -1,   -1,  308,  356,  357,  358,  359,  360,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,
  381,  382,   -1,  384,  385,  386,  387,   -1,  389,  390,
  391,  392,  393,  394,  308,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,  308,  389,  390,  391,  392,  393,
  394,   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  369,   -1,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,  308,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  370,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,   -1,   -1,  308,   -1,  352,   -1,   -1,   -1,  356,
  357,  358,  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,  375,  376,
  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,
  387,   -1,  389,  390,  391,  392,  393,  394,  353,  308,
   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
  308,   -1,   -1,   -1,   -1,   -1,  355,  356,  357,  358,
  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,  308,
  389,  390,  391,  392,  393,  394,   -1,   -1,  356,  357,
  358,  359,  360,   -1,  362,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  373,  374,  375,  376,  377,
  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,
  308,  389,  390,  391,  392,  393,  394,  356,  357,  358,
  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  369,   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,  308,
  389,  390,  391,  392,  393,  394,   -1,   -1,  356,  357,
  358,  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  369,   -1,   -1,   -1,  373,  374,  375,  376,  377,
  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,
  308,  389,  390,  391,  392,  393,  394,  356,  357,  358,
  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  369,   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,
  389,  390,  391,  392,  393,  394,  308,   -1,  356,  357,
  358,  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  369,   -1,   -1,   -1,  373,  374,  375,  376,  377,
  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,
   -1,  389,  390,  391,  392,  393,  394,   -1,  308,   -1,
   -1,  353,   -1,   -1,  356,  357,  358,  359,  360,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,  381,
  382,   -1,  384,  385,  386,  387,   -1,  389,  390,  391,
  392,  393,  394,  353,  308,   -1,  356,  357,  358,  359,
  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,   -1,
   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,  389,
  390,  391,  392,  393,  394,   -1,  308,   -1,   -1,  353,
   -1,   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,  353,  308,   -1,  356,  357,  358,  359,  360,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,  381,
  382,   -1,  384,  385,  386,  387,   -1,  389,  390,  391,
  392,  393,  394,   -1,  308,   -1,   -1,  353,   -1,   -1,
  356,  357,  358,  359,  360,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,  374,  375,
  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,  385,
  386,  387,   -1,  389,  390,  391,  392,  393,  394,  353,
  308,   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,   -1,   -1,   -1,  308,   -1,  354,   -1,  356,  357,
  358,  359,  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  373,  374,  375,  376,  377,
  378,   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,
   -1,  389,  390,  391,  392,  393,  394,  351,  308,  353,
   -1,   -1,  356,  357,  358,  359,  360,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  373,
  374,  375,  376,  377,  378,   -1,   -1,   -1,  382,   -1,
  384,  385,  386,  387,   -1,  389,  390,  391,  392,  393,
  394,   -1,   -1,   -1,   -1,   -1,  356,  357,  358,  359,
  360,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  373,  374,  375,  376,  377,  378,   -1,
   -1,  381,  382,   -1,  384,  385,  386,  387,   -1,  389,
  390,  391,  392,  393,  394,  357,  358,  359,  360,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  373,  374,  375,  376,  377,  378,   -1,   -1,  381,
  382,  360,  384,  385,  386,  387,   -1,  389,  390,  391,
  392,  393,  394,   -1,  373,  374,  375,  376,  377,  378,
   -1,   -1,  381,  382,   -1,  384,  385,  386,  387,  364,
  389,  390,  391,  392,  393,  394,   -1,   -1,  373,  374,
  375,  376,  377,  378,   -1,   -1,  381,  382,   -1,  384,
  385,  386,  387,   -1,  389,  390,  391,  392,  393,  394,
};
}
final static short YYFINAL=94;
final static short YYMAXTOKEN=446;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"MINTOKEN","FORTIMES","FORIN","MAKELIST","SUB","SUBLIST","BOOL",
"LOC","STATE","STATIC","LIST","CALL","iCALL","iEXISTS","iPROCESS","iPREFIX",
"iFOR","iFORTIMES","iFORIN","iPROJ","iSEMI","iAND","ENV","BND","MEMBLOCK","iIF",
"iSYSTEM","iLEN","iOR","iUNDEFINED","RUN","WHILE","FOR","IN","TIMES","REPEAT",
"EXISTS","FORALL","TO","SIZE","SKIP","EMPTY","MORE","LAMBDA","RANLEN",
"INFINITE","END","NAME","STRING","FLOAT","INT","PROJ","FIXLIST","VARLIST",
"NPREV","SHOWMEM","EXIT","SHOWTRACE","SHOWSTATE","ABORT","BREAK","RETURN",
"CONTINUE","LEAP","STEP","PASSLIMIT","VERBOSITY","PRINTLEVEL","DEBUGLEVEL",
"STATEPRINT","REDUNDANCY","CALLBYNAME","ASSIGNAHEAD","BREAKISABORT","HOPCHOP",
"PATH","HELPDIR","INFILE","OUTFILE","TIMESTEP","RANDSEED","RRANDSEED",
"SRANDSEED","SRRANDSEED","MAXRANDLEN","PRECISION","RANDOM","RRANDOM","SRANDOM",
"SRRANDOM","NIL","LBRACE","PROCESS","LPAREN","LBRACK","RBRACE","RPAREN",
"RBRACK","BAR","SEMI","IMPLIES","OR","WHERE","AND","IF","THEN","IFX","ELSE",
"DEFINE","SET","FORMAT","ATANTWO","DO","UNTIL","HISTORY","HELP","GETS","IS",
"ASSN","UASSN","IASSN","DEFAULTS","KEEP","THE","LT","LE","EQ","GE","GT","NEQ",
"DOUBLEEQ","LEN","PLUS","MINUS","MULT","DIV","MOD","POWER","PREFIX","ALWAYS",
"SOMETIME","HALT","FIN","INIT","STB","MEM","INPUT","WINPUT","OUTPUT","PEEK",
"CHOPSTAR","NOT","NEXT","WNEXT","PREV","TYPE","ASCII","STRUCT","LOAD","SYSTEM",
"UMINUS","UPLUS","CEIL","FLOOR","SQRT","ITOF","EXPO","LOG","LOGTEN","SIN","COS",
"TAN","ASIN","ACOS","ATAN","SINH","COSH","TANH","FABS","REFER","DEREF","COMMA",
"MAXTOKEN","MQS","IDN","MDF","EIF","H1","STL","IPTL",
};
final static String yyrule[] = {
"$accept : program",
"program : non_empty_expression_end_list",
"non_empty_expression_end_list : program expression END",
"non_empty_expression_end_list : expression END",
"expression : LBRACE expression RBRACE",
"expression : LPAREN expression RPAREN",
"expression : IF expression THEN expression",
"expression : IF expression THEN expression ELSE expression",
"expression : expression IMPLIES expression",
"expression : WHILE expression DO expression",
"expression : FOR NAME IN expression DO expression",
"expression : FOR expression TIMES DO expression",
"expression : FOR for_range DO expression",
"expression : CHOPSTAR expression",
"expression : REPEAT expression UNTIL expression",
"expression : EXISTS name_list DO expression",
"expression : EXISTS name_list HISTORY expression DO expression",
"expression : EXISTS for_range DO expression",
"expression : EXISTS for_range HISTORY expression DO expression",
"expression : FORALL for_range DO expression",
"expression : RUN expression",
"expression : LOAD expression",
"expression : FORMAT LPAREN expression_list RPAREN",
"expression : OUTPUT expression",
"expression : WINPUT expression",
"expression : INPUT expression",
"expression : PEEK expression",
"expression : DEFINE NAME IASSN expression",
"expression : DEFINE NAME LPAREN name_list RPAREN IASSN expression",
"expression : TYPE expression",
"expression : ASCII expression",
"expression : SET set_expression IASSN expression",
"expression : HELP expression",
"expression : SYSTEM expression",
"expression : NAME LPAREN expression_list RPAREN",
"expression : LAMBDA LPAREN name_list RPAREN DO expression",
"expression : THE expression DO expression",
"expression : SHOWMEM LPAREN expression COMMA expression RPAREN",
"expression : expression OR expression",
"expression : expression AND expression",
"expression : expression WHERE expression",
"expression : NOT expression",
"expression : expression SEMI expression",
"expression : expression GETS expression",
"expression : expression IS expression",
"expression : expression ASSN expression",
"expression : expression UASSN expression",
"expression : expression IASSN expression",
"expression : expression DEFAULTS expression",
"expression : PROCESS expression",
"expression : PREFIX expression",
"expression : expression PROJ expression",
"expression : ALWAYS expression",
"expression : SOMETIME expression",
"expression : HALT expression",
"expression : LEN expression",
"expression : MEM expression",
"expression : FIN expression",
"expression : INIT expression",
"expression : KEEP expression",
"expression : STB expression",
"expression : STRUCT expression",
"expression : NEXT expression",
"expression : WNEXT expression",
"expression : PREV expression",
"expression : NPREV LPAREN expression COMMA expression RPAREN",
"expression : expression LT expression",
"expression : expression LE expression",
"expression : expression DOUBLEEQ expression",
"expression : expression GE expression",
"expression : expression GT expression",
"expression : expression NEQ expression",
"expression : REFER expression",
"expression : DEREF expression",
"expression : PLUS expression",
"expression : MINUS expression",
"expression : expression PLUS expression",
"expression : expression MINUS expression",
"expression : expression MULT expression",
"expression : expression DIV expression",
"expression : expression MOD expression",
"expression : expression POWER expression",
"expression : CEIL expression",
"expression : FLOOR expression",
"expression : SQRT expression",
"expression : ITOF expression",
"expression : EXPO expression",
"expression : LOG expression",
"expression : LOGTEN expression",
"expression : SIN expression",
"expression : COS expression",
"expression : TAN expression",
"expression : ASIN expression",
"expression : ACOS expression",
"expression : ATAN expression",
"expression : SINH expression",
"expression : COSH expression",
"expression : TANH expression",
"expression : FABS expression",
"expression : ATANTWO LPAREN expression COMMA expression RPAREN",
"expression : FIXLIST LPAREN expression COMMA expression RPAREN",
"expression : VARLIST LPAREN expression COMMA expression RPAREN",
"expression : BAR expression BAR",
"expression : list_expression",
"expression : atom",
"expression : error",
"list_expression : list",
"list_expression : sublist",
"list : LBRACK expression_list RBRACK",
"sublist : list_expr LBRACK RBRACK",
"sublist : list_expr LBRACK expression RBRACK",
"sublist : list_expr LBRACK expression TO expression RBRACK",
"list_expr : NAME",
"list_expr : NAME LPAREN expression_list RPAREN",
"list_expr : list_expression",
"atom : NAME",
"atom : BOOL",
"atom : NIL",
"atom : STRING",
"atom : EMPTY",
"atom : SKIP",
"atom : MORE",
"atom : RANLEN",
"atom : INFINITE",
"atom : RANDOM",
"atom : RRANDOM",
"atom : SRANDOM",
"atom : SRRANDOM",
"atom : LEAP",
"atom : STEP",
"atom : BREAK",
"atom : CONTINUE",
"atom : RETURN",
"atom : ABORT",
"atom : SHOWTRACE",
"atom : SHOWSTATE",
"atom : EXIT",
"atom : INT",
"atom : FLOAT",
"set_expression : PASSLIMIT",
"set_expression : VERBOSITY",
"set_expression : PRINTLEVEL",
"set_expression : DEBUGLEVEL",
"set_expression : STATEPRINT",
"set_expression : REDUNDANCY",
"set_expression : CALLBYNAME",
"set_expression : ASSIGNAHEAD",
"set_expression : BREAKISABORT",
"set_expression : HOPCHOP",
"set_expression : PATH",
"set_expression : HELPDIR",
"set_expression : INFILE",
"set_expression : OUTFILE",
"set_expression : TIMESTEP",
"set_expression : RANDSEED",
"set_expression : RRANDSEED",
"set_expression : SRANDSEED",
"set_expression : SRRANDSEED",
"set_expression : MAXRANDLEN",
"set_expression : PRECISION",
"name_list :",
"name_list : non_empty_name_list",
"non_empty_name_list : NAME",
"non_empty_name_list : NAME COMMA non_empty_name_list",
"expression_list :",
"expression_list : non_empty_expression_list",
"expression_list : LPAREN expression_list RPAREN",
"non_empty_expression_list : expression",
"non_empty_expression_list : expression COMMA non_empty_expression_list",
"for_range : NAME LT expression",
"for_range : LPAREN NAME LT expression RPAREN",
};

//#line 348 "interpreter.y"
  
  public NodeType RootNodeOfExpressionTree;
  private Yylex lexer;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.out.println ("Error: " + error);
  }

  public Parser(Reader r) {
       lexer = new Yylex(r, this);
     }
   

    

 

//#line 1660 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 105 "interpreter.y"
{yyval.obj=val_peek(0).obj; RootNodeOfExpressionTree=((NodeType)yyval.obj);}
break;
case 2:
//#line 109 "interpreter.y"
{yyval.obj=new BasicNode(new AndOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(1).obj);}
break;
case 3:
//#line 110 "interpreter.y"
{yyval.obj=val_peek(1).obj;}
break;
case 4:
//#line 115 "interpreter.y"
{yyval.obj=val_peek(1).obj;}
break;
case 5:
//#line 116 "interpreter.y"
{yyval.obj=val_peek(1).obj;}
break;
case 6:
//#line 119 "interpreter.y"
{yyval.obj=new BasicNode(new IfOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj,new BooleanConstantNode(true)); }
break;
case 7:
//#line 120 "interpreter.y"
{yyval.obj=new BasicNode(new IfOperator(),(NodeType)val_peek(4).obj,(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);}
break;
case 8:
//#line 121 "interpreter.y"
{yyval.obj=new BasicNode(new ImpliesOperator(), (NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj );}
break;
case 9:
//#line 124 "interpreter.y"
{yyval.obj=new BasicNode(new WhileLoop(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);}
break;
case 10:
//#line 125 "interpreter.y"
{yyval.obj=new BasicNode(new ForinLoop(),(NodeType)val_peek(4).obj,(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);}
break;
case 11:
//#line 126 "interpreter.y"
{yyval.obj=new BasicNode(new ForTimesLoop(),(NodeType)val_peek(3).obj,(NodeType)val_peek(0).obj);}
break;
case 13:
//#line 128 "interpreter.y"
{yyval.obj=new BasicNode(new ChopstarOperator(),(NodeType)val_peek(0).obj);}
break;
case 14:
//#line 129 "interpreter.y"
{yyval.obj=new BasicNode(new RepeatOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);}
break;
case 15:
//#line 132 "interpreter.y"
{yyval.obj=new BasicNode(new ExistsOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);}
break;
case 20:
//#line 139 "interpreter.y"
{yyval.obj=new BasicNode(new RunOperator(),(NodeType)val_peek(0).obj,new BooleanConstantNode(true)); }
break;
case 21:
//#line 140 "interpreter.y"
{yyval.obj=new BasicNode(new LoadOperator(),(NodeType)val_peek(0).obj); }
break;
case 22:
//#line 141 "interpreter.y"
{ yyval.obj=new BasicNode(new FormatOperator(),(NodeType)val_peek(1).obj); }
break;
case 23:
//#line 142 "interpreter.y"
{ yyval.obj=new BasicNode(new OutputOperator(),(NodeType)val_peek(0).obj); }
break;
case 24:
//#line 143 "interpreter.y"
{  }
break;
case 25:
//#line 144 "interpreter.y"
{ yyval.obj=new BasicNode(new InputOperator(),(NodeType)val_peek(0).obj); }
break;
case 26:
//#line 145 "interpreter.y"
{  }
break;
case 27:
//#line 146 "interpreter.y"
{yyval.obj=new BasicNode(new DefineOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); /*add to function table the expression*/}
break;
case 28:
//#line 147 "interpreter.y"
{yyval.obj=new BasicNode(new DefineOperator(),(NodeType)val_peek(5).obj,new BasicNode(new LambdaOperator(),(NodeType)val_peek(3).obj, (NodeType)val_peek(0).obj)); /*add to function table the expression*/}
break;
case 29:
//#line 148 "interpreter.y"
{yyval.obj=new BasicNode(new TypeOperator(),(NodeType)val_peek(0).obj); }
break;
case 30:
//#line 149 "interpreter.y"
{yyval.obj=new BasicNode(new AsciiOperator(),(NodeType)val_peek(0).obj); }
break;
case 31:
//#line 150 "interpreter.y"
{yyval.obj=new BasicNode(new SetOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 32:
//#line 151 "interpreter.y"
{  }
break;
case 33:
//#line 152 "interpreter.y"
{  }
break;
case 34:
//#line 153 "interpreter.y"
{ yyval.obj=new BasicNode(new CallOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);}
break;
case 35:
//#line 154 "interpreter.y"
{yyval.obj=new BasicNode(new LambdaOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(0).obj); }
break;
case 36:
//#line 155 "interpreter.y"
{  }
break;
case 37:
//#line 156 "interpreter.y"
{    }
break;
case 38:
//#line 159 "interpreter.y"
{ yyval.obj=new BasicNode(new OrOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 39:
//#line 160 "interpreter.y"
{yyval.obj=new BasicNode(new AndOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);   }
break;
case 40:
//#line 161 "interpreter.y"
{yyval.obj=new BasicNode(new WhereOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 41:
//#line 162 "interpreter.y"
{ yyval.obj=new BasicNode(new UnaryNotOperator(),(NodeType)val_peek(0).obj); }
break;
case 42:
//#line 165 "interpreter.y"
{yyval.obj=new BasicNode(new SemiOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 43:
//#line 166 "interpreter.y"
{yyval.obj=new BasicNode(new GetsOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 44:
//#line 167 "interpreter.y"
{ yyval.obj=new BasicNode(new IsOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 45:
//#line 168 "interpreter.y"
{yyval.obj=new BasicNode(new AssignmentOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 46:
//#line 169 "interpreter.y"
{yyval.obj=new BasicNode(new UassignOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 47:
//#line 170 "interpreter.y"
{yyval.obj=new BasicNode(new IassignOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 48:
//#line 171 "interpreter.y"
{yyval.obj=new BasicNode(new DefaultsOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 49:
//#line 172 "interpreter.y"
{yyval.obj=new BasicNode(new ProcessOperator(),(NodeType)val_peek(0).obj);  }
break;
case 50:
//#line 173 "interpreter.y"
{ yyval.obj=new BasicNode(new PrefixOperator(),(NodeType)val_peek(0).obj); }
break;
case 51:
//#line 174 "interpreter.y"
{ yyval.obj=new BasicNode(new ProjOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 52:
//#line 175 "interpreter.y"
{ yyval.obj=new BasicNode(new AlwaysOperator(),(NodeType)val_peek(0).obj); }
break;
case 53:
//#line 176 "interpreter.y"
{ yyval.obj=new BasicNode(new SometimeOperator(),(NodeType)val_peek(0).obj); }
break;
case 54:
//#line 177 "interpreter.y"
{ yyval.obj=new BasicNode(new HaltOperator(),(NodeType)val_peek(0).obj); }
break;
case 55:
//#line 178 "interpreter.y"
{ yyval.obj=new BasicNode(new LenOperator(),(NodeType)val_peek(0).obj); }
break;
case 56:
//#line 179 "interpreter.y"
{ yyval.obj=new BasicNode(new MemOperator(),(NodeType)val_peek(0).obj); }
break;
case 57:
//#line 180 "interpreter.y"
{ yyval.obj=new BasicNode(new FinOperator(),(NodeType)val_peek(0).obj); }
break;
case 58:
//#line 181 "interpreter.y"
{ yyval.obj=new BasicNode(new InitOperator(),(NodeType)val_peek(0).obj); }
break;
case 59:
//#line 182 "interpreter.y"
{ yyval.obj=new BasicNode(new KeepOperator(),(NodeType)val_peek(0).obj);}
break;
case 60:
//#line 183 "interpreter.y"
{  yyval.obj=new BasicNode(new StbOperator(),(NodeType)val_peek(0).obj);}
break;
case 61:
//#line 184 "interpreter.y"
{ yyval.obj=new BasicNode(new StructOperator(),(NodeType)val_peek(0).obj); }
break;
case 62:
//#line 185 "interpreter.y"
{ yyval.obj=new BasicNode(new NextOperator(),(NodeType)val_peek(0).obj); }
break;
case 63:
//#line 186 "interpreter.y"
{ yyval.obj=new BasicNode(new WnextOperator(),(NodeType)val_peek(0).obj); }
break;
case 64:
//#line 187 "interpreter.y"
{  yyval.obj=new BasicNode(new PrevOperator(),(NodeType)val_peek(0).obj);}
break;
case 65:
//#line 188 "interpreter.y"
{ yyval.obj=new BasicNode(new NprevOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj); }
break;
case 66:
//#line 191 "interpreter.y"
{yyval.obj=new BasicNode(new LessThanOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 67:
//#line 192 "interpreter.y"
{yyval.obj=new BasicNode(new LessThanEqualToOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 68:
//#line 193 "interpreter.y"
{yyval.obj=new BasicNode(new IsEqualToOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 69:
//#line 194 "interpreter.y"
{yyval.obj=new BasicNode(new GreaterThanEqualToOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 70:
//#line 195 "interpreter.y"
{yyval.obj=new BasicNode(new GreaterThanOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 71:
//#line 196 "interpreter.y"
{yyval.obj=new BasicNode(new NotEqualToOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 72:
//#line 199 "interpreter.y"
{  yyval.obj=new BasicNode(new ReferOperator(),(NodeType)val_peek(0).obj);}
break;
case 73:
//#line 200 "interpreter.y"
{  yyval.obj=new BasicNode(new DerefOperator(),(NodeType)val_peek(0).obj); }
break;
case 74:
//#line 203 "interpreter.y"
{  }
break;
case 75:
//#line 204 "interpreter.y"
{yyval.obj=new BasicNode(new UnaryNegationOperator(),(NodeType)val_peek(0).obj);  }
break;
case 76:
//#line 205 "interpreter.y"
{yyval.obj=new BasicNode(new AddOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 77:
//#line 206 "interpreter.y"
{yyval.obj=new BasicNode(new MinusOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 78:
//#line 207 "interpreter.y"
{yyval.obj=new BasicNode(new MultOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj);  }
break;
case 79:
//#line 208 "interpreter.y"
{ yyval.obj=new BasicNode(new DivOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 80:
//#line 209 "interpreter.y"
{ yyval.obj=new BasicNode(new ModOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 81:
//#line 210 "interpreter.y"
{ yyval.obj=new BasicNode(new PowerOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 82:
//#line 211 "interpreter.y"
{ yyval.obj=new BasicNode(new CeilOperator(),(NodeType)val_peek(0).obj);  }
break;
case 83:
//#line 212 "interpreter.y"
{ yyval.obj=new BasicNode(new FloorOperator(),(NodeType)val_peek(0).obj); }
break;
case 84:
//#line 213 "interpreter.y"
{ yyval.obj=new BasicNode(new SqrtOperator(),(NodeType)val_peek(0).obj); }
break;
case 85:
//#line 214 "interpreter.y"
{ yyval.obj=new BasicNode(new ItofOperator(),(NodeType)val_peek(0).obj); }
break;
case 86:
//#line 215 "interpreter.y"
{ yyval.obj=new BasicNode(new ExpoOperator(),(NodeType)val_peek(0).obj); }
break;
case 87:
//#line 216 "interpreter.y"
{ yyval.obj=new BasicNode(new LogOperator(),(NodeType)val_peek(0).obj); }
break;
case 88:
//#line 217 "interpreter.y"
{ yyval.obj=new BasicNode(new Log10Operator(),(NodeType)val_peek(0).obj); }
break;
case 89:
//#line 218 "interpreter.y"
{ yyval.obj=new BasicNode(new SinOperator(),(NodeType)val_peek(0).obj); }
break;
case 90:
//#line 219 "interpreter.y"
{  yyval.obj=new BasicNode(new CosOperator(),(NodeType)val_peek(0).obj);}
break;
case 91:
//#line 220 "interpreter.y"
{  yyval.obj=new BasicNode(new TanOperator(),(NodeType)val_peek(0).obj);}
break;
case 92:
//#line 221 "interpreter.y"
{ yyval.obj=new BasicNode(new AsinOperator(),(NodeType)val_peek(0).obj); }
break;
case 93:
//#line 222 "interpreter.y"
{ yyval.obj=new BasicNode(new AcosOperator(),(NodeType)val_peek(0).obj); }
break;
case 94:
//#line 223 "interpreter.y"
{ yyval.obj=new BasicNode(new AtanOperator(),(NodeType)val_peek(0).obj); }
break;
case 95:
//#line 224 "interpreter.y"
{  yyval.obj=new BasicNode(new SinhOperator(),(NodeType)val_peek(0).obj);}
break;
case 96:
//#line 225 "interpreter.y"
{ yyval.obj=new BasicNode(new CoshOperator(),(NodeType)val_peek(0).obj); }
break;
case 97:
//#line 226 "interpreter.y"
{ yyval.obj=new BasicNode(new TanhOperator(),(NodeType)val_peek(0).obj); }
break;
case 98:
//#line 227 "interpreter.y"
{ yyval.obj=new BasicNode(new FabsOperator(),(NodeType)val_peek(0).obj); }
break;
case 99:
//#line 228 "interpreter.y"
{  }
break;
case 100:
//#line 233 "interpreter.y"
{yyval.obj=new BasicNode(new FixListOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);  }
break;
case 101:
//#line 234 "interpreter.y"
{yyval.obj=new BasicNode(new VarListOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);  }
break;
case 102:
//#line 235 "interpreter.y"
{yyval.obj=new BasicNode(new SizeOperator(),(NodeType)val_peek(1).obj);  }
break;
case 104:
//#line 239 "interpreter.y"
{}
break;
case 106:
//#line 244 "interpreter.y"
{yyval.obj=val_peek(0).obj;}
break;
case 107:
//#line 245 "interpreter.y"
{yyval.obj=val_peek(0).obj;}
break;
case 108:
//#line 249 "interpreter.y"
{ yyval.obj=new BasicNode(new MakeListOperator(),(NodeType)val_peek(1).obj); }
break;
case 109:
//#line 253 "interpreter.y"
{yyval.obj=new BasicNode(new SubOperator(),(NodeType)val_peek(2).obj,null);  }
break;
case 110:
//#line 254 "interpreter.y"
{yyval.obj=new BasicNode(new SubOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);  }
break;
case 111:
//#line 255 "interpreter.y"
{yyval.obj=new BasicNode(new SubList(),(NodeType)val_peek(5).obj,(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);  }
break;
case 113:
//#line 260 "interpreter.y"
{yyval.obj=new BasicNode(new CallOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj);}
break;
case 115:
//#line 268 "interpreter.y"
{yyval.obj=(IdentifierNode)val_peek(0).obj;}
break;
case 116:
//#line 269 "interpreter.y"
{yyval.obj=(BooleanConstantNode)val_peek(0).obj;}
break;
case 117:
//#line 270 "interpreter.y"
{yyval.obj=(NilConstantNode)val_peek(0).obj;}
break;
case 118:
//#line 271 "interpreter.y"
{yyval.obj=(StringConstantNode)val_peek(0).obj;}
break;
case 119:
//#line 272 "interpreter.y"
{yyval.obj=(EmptyConstantNode)val_peek(0).obj;}
break;
case 120:
//#line 273 "interpreter.y"
{yyval.obj=(SkipConstantNode)val_peek(0).obj;}
break;
case 121:
//#line 274 "interpreter.y"
{yyval.obj=(MoreConstantNode)val_peek(0).obj;}
break;
case 122:
//#line 275 "interpreter.y"
{    }
break;
case 123:
//#line 276 "interpreter.y"
{     }
break;
case 124:
//#line 277 "interpreter.y"
{     }
break;
case 125:
//#line 278 "interpreter.y"
{    }
break;
case 126:
//#line 279 "interpreter.y"
{    }
break;
case 127:
//#line 280 "interpreter.y"
{    }
break;
case 128:
//#line 281 "interpreter.y"
{}
break;
case 129:
//#line 282 "interpreter.y"
{}
break;
case 130:
//#line 283 "interpreter.y"
{}
break;
case 131:
//#line 284 "interpreter.y"
{}
break;
case 132:
//#line 285 "interpreter.y"
{}
break;
case 133:
//#line 286 "interpreter.y"
{}
break;
case 134:
//#line 287 "interpreter.y"
{}
break;
case 135:
//#line 288 "interpreter.y"
{}
break;
case 136:
//#line 289 "interpreter.y"
{}
break;
case 137:
//#line 290 "interpreter.y"
{yyval.obj=(IntConstantNode)val_peek(0).obj;}
break;
case 138:
//#line 291 "interpreter.y"
{}
break;
case 160:
//#line 319 "interpreter.y"
{ yyval.obj=null;}
break;
case 161:
//#line 320 "interpreter.y"
{yyval.obj=val_peek(0).obj;}
break;
case 162:
//#line 325 "interpreter.y"
{yyval.obj=(IdentifierNode)val_peek(0).obj;}
break;
case 163:
//#line 326 "interpreter.y"
{ yyval.obj=new BasicNode(new CommaOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 164:
//#line 331 "interpreter.y"
{ yyval.obj=null; }
break;
case 165:
//#line 332 "interpreter.y"
{yyval.obj=val_peek(0).obj;}
break;
case 166:
//#line 333 "interpreter.y"
{ yyval.obj=val_peek(1).obj; }
break;
case 167:
//#line 337 "interpreter.y"
{yyval.obj=new BasicNode(new CommaOperator(),(NodeType)val_peek(0).obj,null);}
break;
case 168:
//#line 338 "interpreter.y"
{ yyval.obj=new BasicNode(new CommaOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 169:
//#line 342 "interpreter.y"
{ yyval.obj=new BasicNode(new LessThanOperator(),(NodeType)val_peek(2).obj,(NodeType)val_peek(0).obj); }
break;
case 170:
//#line 343 "interpreter.y"
{yyval.obj=new BasicNode(new LessThanOperator(),(NodeType)val_peek(3).obj,(NodeType)val_peek(1).obj); }
break;
//#line 2369 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
