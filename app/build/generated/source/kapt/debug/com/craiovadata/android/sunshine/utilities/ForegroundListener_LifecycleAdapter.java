package com.craiovadata.android.sunshine.utilities;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;
import java.lang.Override;

public class ForegroundListener_LifecycleAdapter implements GeneratedAdapter {
  final ForegroundListener mReceiver;

  ForegroundListener_LifecycleAdapter(ForegroundListener receiver) {
    this.mReceiver = receiver;
  }

  @Override
  public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny,
      MethodCallsLogger logger) {
    boolean hasLogger = logger != null;
    if (onAny) {
      return;
    }
    if (event == Lifecycle.Event.ON_START) {
      if (!hasLogger || logger.approveCall("onStart", 1)) {
        mReceiver.onStart();
      }
      return;
    }
    if (event == Lifecycle.Event.ON_STOP) {
      if (!hasLogger || logger.approveCall("onStop", 1)) {
        mReceiver.onStop();
      }
      return;
    }
  }
}