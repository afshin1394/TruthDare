package com.afshin.truthordare.Utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class SingleLiveData<T> extends MutableLiveData<T> {
    private final AtomicBoolean mPending = new AtomicBoolean(false);
    private static final String TAG = "SingleLiveEvent";
    @NotNull
    public static final SingleLiveData.Companion Companion = new SingleLiveData.Companion((DefaultConstructorMarker)null);

    @MainThread
    public void observe(@NotNull LifecycleOwner owner, @NotNull final Observer observer) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        if (this.hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        super.observe(owner, (Observer) t -> {
            if (SingleLiveData.this.mPending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }

        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        this.mPending.set(true);
        super.setValue(t);
    }

    @MainThread
    public final void call() {
        this.setValue(null);
    }

    @Metadata(
            mv = {1, 6, 0},
            k = 1,
            d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\u0005"},
            d2 = {"LSingleLiveData$Companion;", "", "()V", "TAG", "", "TruthORDare.app"}
    )
    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
