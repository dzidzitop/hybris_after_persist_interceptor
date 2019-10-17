/* Copyright (c) 2019, Dźmitry Laŭčuk
   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are met: 

   1. Redistributions of source code must retain the above copyright notice, this
      list of conditions and the following disclaimer.
   2. Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
   DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
   ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */
package afc.hybris;

import de.hybris.platform.servicelayer.interceptor.impl.DefaultInterceptorRegistry;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class CustomInterceptorRegistry extends DefaultInterceptorRegistry
{
    /* The superclass cannot be GCed until this class is unloaded so keeping here
     * a hard reference to the 'findInterceptors' function does not affect ability
     * to unload the superclass.
     */
    private static Method findInterceptorsMethod;
    
    static
    {
        try {
            findInterceptorsMethod = CustomInterceptorRegistry.class.getSuperclass().
                    getDeclaredMethod("findInterceptors", String.class, Class.class);
            findInterceptorsMethod.setAccessible(true);
        }
        catch (NoSuchMethodException ex) {
            throw new ExceptionInInitializerError(ex);
        }
        catch (SecurityException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Collection<AfterPersistInterceptor<?>> getAfterPersistInterceptors(final String type)
    {
        assertLoaded();
        try {
            return (Collection<AfterPersistInterceptor<?>>) findInterceptorsMethod.invoke(
                    this, type, AfterPersistInterceptor.class);
        }
        catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }
}
