# Review Changes 
1. MAVEN POM Delete Parent introduce BOM - Antipatter!!
2. MAVEN Delete unnecessary Dependencies 
3. MAVEN POM Rename the Group an Artifact ID etc. 


# Review Open Issues 

1. Github Short Project Description Abstract
2. Do we need WsfInboundFrameEvent WsfOubboundFrameEvent? I think no!! 
-> used in:
public class GsWebSocketHandler extends AbstractWebSocketHandler implements ApplicationListener<WsfInboundFrameEvent>, SubProtocolCapable
-> method:
public void onApplicationEvent(WsfInboundFrameEvent event)
-> i don't know if it is used when running (tku)
3. Then we could Delete The Inbound Tests!? 
4. Delete the Exception Test 
-> done! (tku)
5. Change the Package Desciption To Base Package com.thinkenterprise.wsf
6. A simple Sample is missing
-> started in code (tku):
src/samples/
7. Delete Autoconfiguration
8. Converters. Why do we have 2 Converter Interfaces an only one implementation 
9. Shot we provide a Util Calss for createData(Set<String> set) in the FrameConverter?
10. Make it to a pure Java - Only Object Mapper comes from spring-boot-starter-json!!

