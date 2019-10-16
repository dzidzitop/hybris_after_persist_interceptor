After-persist interceptor for Hybris
===========

This project is a patch to Hybris that adds an after-persist interceptor to the list of the out-of-the-box intercetptors.

Introduction
------------

There is a number of pre-existing Hybris interceptors that help us to validate and prepare Hybris model items and listen for item loading and removing occasions. However, there is no capability to track item modifications to do some background actions on item updates, say, sending notifications, logging or cleaning custom caches that cache data that has been potentially changed. This patch introduces the `afc.hybris.AfterPersistInterceptor` interface that can help with this.

Installation
------------

Installation is pretty simple - just copy the Java source code from this patch to any of your Hybris extensions and copy the contents of `src/resources/spring.xml` to plug the custom code to your Hybris runtime.

Implementing your own after-persist inteceptor
------------

Generally, an after-persist interceptor is implement like any other out-of-the-box interceptor. Here are the key steps:

* Create a class that implements `afc.hybris.AfterPersistInterceptor`
* Add it to the interceptor registry, e.g.:

~~~
	<bean id="yourCustomAfterPersistInterceptor" class="com.test.YourCustomAfterPersistInterceptor" scope="tenant"/>
	<bean id="yourCustomAfterPersistInterceptorMapping" class="de.hybris.platform.servicelayer.interceptor.impl.InterceptorMapping" scope="tenant">
		<property name="interceptor" ref="yourCustomAfterPersistInterceptor"/>
		<property name="typeCode" value="TheModelTypeToListenFor"/>
	</bean>
~~~

System requirements
------------

* Java 1.5+
* Hybris 5.6+
