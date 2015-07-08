To run the example on Apache ServiceMix 4.x or Apache Karaf 2.2.x

1) launch the server
karaf.bat
  
Note for Karaf 2.2.x: 
  a) edit the etc/jre.properties file to add the following packages to be exported
  jre-1.6=, \
  com.sun.org.apache.xerces.internal.dom, \
  com.sun.org.apache.xerces.internal.jaxp, \

  b) from the same file comment out the following exports already provided by the bundles
  that will be imported next: javax.xml.bind*, javax.jws*, javax.xml.soap*, javax.xml.ws*, 
  javax.activation, javax.annotation, javax.xml.stream*.


2) Add features required
features:addUrl mvn:org.apache.camel.karaf/apache-camel/${version}/xml/features
features:install war
features:install cxf
features:install camel-spring
features:install camel-jaxb
features:install camel-cxf
  

3) Deploy the example
osgi:install -s mvn:org.apache.camel/camel-example-cxf-osgi/${version}
 

