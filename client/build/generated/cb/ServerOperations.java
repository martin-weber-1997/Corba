package cb;


/**
 * Generated from IDL interface "Server".
 *
 * @author JacORB IDL compiler V 3.7
 * @version generated at Apr 13, 2016 7:30:06 PM
 */

public interface ServerOperations
{
	/* constants */
	/* operations  */
	void one_time(cb.CallBack _cb, java.lang.String mesg);
	void register(cb.CallBack _cb, java.lang.String mesg, short period_secs);
	void shutdown();
}
