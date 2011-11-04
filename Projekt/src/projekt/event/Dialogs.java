package projekt.event;

/**
 *
 * @author johannes 
 */
public final class Dialogs {
	public static final int MAXMESSAGELENGTH=100;
	public static final class Begin{
		public static final String sayHello="hej\thej! jag \nheter johannes\n och detta ar ett test pa en lite langre strang, denna text ligger i en statisk klass: Dialogs.Begin.sayHello...\tNytt medelande som ocksa kan vara intressant att lasa :=)";
	}
	public static boolean endof=true;
	public static String []message;
	public static void initDialog(String mes){
		//if(Dialogs.message.length >MAXMESSAGELENGTH)
			// TODO fix splitting of the message
		if(endof){
			Dialogs.message=mes.split("\t");
			Dialogs.endof=false;
		}
	}
	private static int ordning = 0;
	public static int getIndex(){
		return ordning;
	}
	public static String nextMessage(){
		String m="";
		if (ordning < Dialogs.message.length){
			Dialogs.ordning++;
			int cur=Dialogs.ordning-1;
			if(Dialogs.ordning==Dialogs.message.length){
				Dialogs.endof=true;
				Dialogs.ordning=0;
			}
			return Dialogs.message[cur];
		}
		Dialogs.endof=true;
		Dialogs.ordning=0;
		return "";
	}
}
