package gsc.practice;

import java.util.Stack;

/**
 *带括号的表达式计算
 *不做表达式正确的判断
 *本类采用利用栈结构解决
 *
 * 
 * 
 * @author acm0
 *
 */
//思路一：使用字符串替换的方法
//这种方法没有实现，写起来应该更加简单，但是有大量的字符串操作

//思路二：本类采用的方法，利用栈结构解决
//运算符带有优先级，')'为0，+-为1，*和/为2,'('做特殊处理
//将运算符和数据分别入栈，在运算符入栈时和前面的运算符做优先级比较。如果前面没有运算符，或者
//优先级大于时，直接入栈。当小于等于时让前一个运算符做运算。算法中把')'的优先级设置为最低，从而当扫描到它时，
//使前面的数据运输。对'('特殊处理
//在一般计算完后，运算符栈中可能会存在多个同样低优先级的运算符，有时候最后一个运算符是高优先级的运算符。这样
//的情况也会发生在一对'('和')'中。应当注意这两点

//相关思考，二叉树，中缀表达式

public class ExpressCalc {
	private static Stack<Double> dataStack = new Stack<Double>();
	private static Stack<Character> operaStack = new Stack<Character>();
	/**
	 * 给定字符串表达式，返回结果
	 * 不做表达式正确的验证
	 * @param express
	 * @return
	 * @throws InvalidExpressExceptin 
	 */
	public static double Calc(String express) throws InvalidExpressExceptin{
		int p1=0;   //两个跟踪字符串中数据的指针
		int p2=0;
		dataStack.clear();
		operaStack.clear();
		 
		while(p2<express.length()){ //这里把运算符和数据分离并入栈处理，
									//在运算符入栈时，会进行算法核心的处理
			char c = express.charAt(p2);
			if( c=='+' || c=='-' || c=='*' || c=='/' || c=='(' || c==')'){//遇到运算符进行处理
				if(p1==p2){
					operaPush(c);
					p1++;
					p2++;
				}else{
					dataPush(Double.parseDouble(express.substring(p1, p2)));
					p1 = p2;
				}
				continue;
			}
			p2++;
		}
		char c = express.charAt(p2-1);
		if( c>='0' && c<='9'){
			dataPush(Double.parseDouble(express.substring(p1, p2)));
		}
		//把栈中剩下的运算完成
		if(!operaStack.isEmpty() && ('*'==(char)operaStack.lastElement() || '/'==(char)operaStack.lastElement())){   
			double d2 = (double) dataStack.pop();		//最后一个运算符优先级有可能大于前边的运算符
			double d1 = (double) dataStack.pop();		//必须先进行计算
			char opera = (char) operaStack.pop();
			dataStack.push(simCalc(d1, d2,opera));
		}
		while(!operaStack.isEmpty()){ //栈中可能还剩下有多个同级的运算符，从左向右开始计算
			double d1 = (double) dataStack.get(0);
			dataStack.remove(0);
			double d2 = (double) dataStack.get(0);
			dataStack.remove(0);
			char opera = (char) operaStack.get(0);
			operaStack.remove(0);
			dataStack.insertElementAt(simCalc(d1, d2,opera), 0);	
		}
		return (double) dataStack.lastElement();//栈中最后一个元素为结果
	}
	private  static void dataPush(double data){
		dataStack.push(data);
	}
	private  static void operaPush(char c) throws InvalidExpressExceptin{
		if(operaStack.isEmpty() || c=='('){     //1.空栈或等于'('时，运算符直接进栈
			operaStack.push(c);					
		}else {
			char befOpera = (char) operaStack.lastElement();
			if(befOpera == '(' || getPriority(c)>getPriority(befOpera)){    //2.优先级高时，进栈,对'('特殊处理
				if(c==')')  //防止"(数字)"这样的情况
					operaStack.pop();
				else
					operaStack.push(c);                                         
			}else {										 //3.运算符小于等于时，计算前一个表达式
				double d2 = (double) dataStack.pop();
				double d1 = (double) dataStack.pop();
				char opera = (char) operaStack.pop();
				dataStack.push(simCalc(d1, d2,opera));	 //将计算结果进栈
				if(c==')'){						 //')'要特殊处理
					//最后一个运算符处理后，在'('的后面还有可能存在多个同级运算符，将他们从左到右计算掉
					//寻找最后一个'('的位置和对应的数据的位置
					int leftIndex = operaStack.size()-1;						
					int dataIndex = dataStack.size()-1;
					for(;'('!=(char)operaStack.get(leftIndex);leftIndex--,dataIndex--);
					leftIndex++;
					//两个位置找到后，将随后的数据都处理掉
					while(leftIndex!=operaStack.size()){
						double dT1 = (double) dataStack.get(dataIndex);
						dataStack.remove(dataIndex);
						double dT2 = (double) dataStack.get(dataIndex);
						dataStack.remove(dataIndex);
						char operaT = (char) operaStack.get(leftIndex);
						operaStack.remove(leftIndex);
						dataStack.insertElementAt(simCalc(dT1, dT2,operaT), dataIndex);	
					}
					operaStack.pop(); //将和')'对应的'('出栈
				}else {
					operaStack.push(c);
				}
			}
		}
	}
	/**
	 * 只有两个数和一个符号的计算
	 * 不做字符串正确检查
	 * @param v1
	 * @param v2
	 * @param opera
	 * @return
	 * @throws InvalidExpressExceptin 
	 */
	public static double simCalc(double v1,double v2,char opera) throws InvalidExpressExceptin {

		switch (opera) {
			case '+':
				return v1+v2;
			case '-':
				return v1-v2;
			case '*':
				return v1*v2;
			case '/':
				return v1/v2;
			default:
				throw new InvalidExpressExceptin("无效的运算符");
		}
		
		
	}
	private  static int getPriority(char c) throws InvalidExpressExceptin{
		if(c==')')
			return 0;
		if(c=='+' || c=='-')
			return 1;
		if(c=='*' || c=='/')
			return 2;
		throw new InvalidExpressExceptin("没有的运算符");
		
	}
	public static void main(String[] args) {
		try {
			System.out.println(ExpressCalc.Calc( "(8*9)+3*2-4" ) );
		} catch (InvalidExpressExceptin e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
class InvalidExpressExceptin extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidExpressExceptin() {
		super();
	}

	public InvalidExpressExceptin(String message) {
		super(message);
	}

	public InvalidExpressExceptin(Throwable cause) {
		super(cause);
	}
	
}