# Crackdoored Source Code
### Backdoored uses a bad para config and no real string encryption; Proxy invokestatics won't save u bro

# How to crack Backdoored 1.6:
 
The developers are so retarded, they made the license key just your backdoored hwid hashed twice with sha512

Here's their HWID gen method:
 
```
private static String getHwid() {
        final String v = System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return Hashing.sha512().hashString((CharSequence)v, StandardCharsets.UTF_8).toString();
    }
```
 
Backdoored 1.6 with spooky dll removed: https://workupload.com/file/JW7PZcrh
Backdoored 1.6 keygen (run with 64 bit java [Program Files/Java] so it generates the right hash): https://workupload.com/file/YApdWpww

Keygen source code; because the developers claim it is a virus because they are scared asf https://pastebin.com/A2kaV59L 
<img src="https://i.imgur.com/Fl1lLzb.jpg" height="40%" width="40%">

## Also, check out the devs shitty skidded """""obfuscator""""" that optimizes your jar for easier cracking by me
https://github.com/cookiedragon234/CObf
