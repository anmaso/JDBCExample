Simple Java class to connect to Oracle database to measure latency

It just queries "Select from dual" or can pass your own query as input parameter

You will need to add ojdbc8.jar in the path in order to compile

If you get the binary release, just execute like:
    
    ./jdbctest jdbc:oracle:thin:@<HOST>:<PORT>/<SID> <USER> <PASS> ["QUERY"]


Since ojdbc driver takes SO user when connecting, it may happen that users with more than 30 chars cause errors, in that case you can define the environment variable JAVA_TOOL_OPTIONS=-Duser.name=my_user


    JAVA_TOOL_OPTIONS=-Duser.name=<USER> ./jdbctest jdbc:oracle:thin:@<HOST>:<PORT>/<SID> <USER> <PASS> ["QUERY"]
