package gsc.practice;

public class MyDate
{
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	private int[] month_day = {31,28,31,30,31,30,31,31,30,31,30,31};

	public MyDate(long time)
	{
		hour =    (int)(( time % (1000*60*60*24) )/ (1000*60*60));
		hour+=8;
		if(hour>23)
			hour-=24;
		minute =  (int)(( time % (1000*60*60) )/ (1000*60));
		second =  (int)(( time % (1000*60) )/1000) ;

		int sumday = (int) ( time / (1000*60*60*24));
		year = 1970;
		month = 1;
		day = 1;
		for(;sumday>0;sumday--)
		{
			addDay();
		}
	//	int sumday = time/60/60/24;

	}
	private void addDay()  //给现有日期加一天;
	{
		if( (0==year%4 && 0!=year%100) || 0==year%400) 
			month_day[1] = 29;
		if(day<month_day[month-1])
		{
			day++;
		}
		else
		{
			day = 1;
			if(month<12)
			{
				month++;
			}
			else
			{
				month = 1;
				year++;
			}
		}
		month_day[1] = 28;
	}
	public String getDate()
	{
		return year+"年"+month+"月"+day+"日"+hour+"时"+minute+"分"+second+"秒";
	}

	public static void main(String[] args)
	{
		MyDate md = new MyDate(System.currentTimeMillis());
		System.out.println(md.getDate());
	}
}