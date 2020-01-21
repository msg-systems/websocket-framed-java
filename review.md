
# Review Open Issues 

2. Do we need WsfInboundFrameEvent? (I)
Please delete!! Check before ... 
```
done! (tku)
```
3. Then we could Delete The Inbound Tests!? (I) 
```
done! (tku)
```
7. Delete Autoconfiguration (II) - Edgar 
8. Converters. Why do we have 2 Converter Interfaces an only one implementation
8.1 Bitte Interfaces zusammenführen XXX unter einem (I)
```
done! (tku)
```
8.2 Bitte Interface XXX auch verwenden ... 
```
done! (tku)
```
8.3 Wenn ein new ... WsfConverter und setter/getter bereitstellen 
```
done!
with 3 beans in GsAutoConfiguration (sorry!)
and constructor parameter in GsWebSocketHandler (tku)
```
9. Shot we provide a Util Calss for createData(Set<String> set) in the FrameConverter? (II)
```
done, but no util class: method in GsWebSocketHandler (tku)
```
10. Make it to a pure Java - Only Object Mapper comes from spring-boot-starter-json!! will be used (III)
POM ->  ObjectMapper from Jackson ... 

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-json</artifactId>
</dependency>

10. Make Tests Pure Java - spring-boot-starter-json .. (I) 
POM ->  JUNIT5 from JUNIT 5 Libs 

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
</dependency>

@SpringBootTest 

GraphQlIoApplicationTests.java	sollen raus
TestApplication.java sollen raus 
```
done! (tku)
```
