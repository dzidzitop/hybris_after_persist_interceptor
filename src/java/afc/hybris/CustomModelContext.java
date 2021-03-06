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

import de.hybris.platform.servicelayer.internal.model.impl.DefaultModelContext;
import de.hybris.platform.servicelayer.internal.model.impl.ModelWrapper;

import java.util.Collection;

public class CustomModelContext extends DefaultModelContext
{
    private final CustomInterceptorRegistry interceptorRegistry;
    
    public CustomModelContext(final CustomInterceptorRegistry interceptorRegistry)
    {
        this.interceptorRegistry = interceptorRegistry;
    }
    
    @Override
    public void afterPersist(final Collection<ModelWrapper> saved)
    {
        super.afterPersist(saved);
        for (final ModelWrapper w : saved) {
            final Collection<AfterPersistInterceptor<?>> interceptors =
                    interceptorRegistry.getAfterPersistInterceptors(w.getPersistenceType());
            for (final AfterPersistInterceptor<?> interceptor : interceptors) {
                interceptor.afterPersist(w.getModel());
            }
        }
    }
}
