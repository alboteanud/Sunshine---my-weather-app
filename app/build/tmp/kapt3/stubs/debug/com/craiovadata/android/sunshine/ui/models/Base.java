package com.craiovadata.android.sunshine.ui.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\b&\u0018\u00002\u00020\u0001:\u0001\u0016B)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bR\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0017"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Base;", "", "_id", "", "_type", "", "_date", "Ljava/util/Date;", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/util/Date;)V", "get_date", "()Ljava/util/Date;", "set_date", "(Ljava/util/Date;)V", "get_id", "()Ljava/lang/Integer;", "set_id", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "get_type", "()Ljava/lang/String;", "set_type", "(Ljava/lang/String;)V", "TYPE", "app_debug"})
public abstract class Base {
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer _id;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String _type;
    @org.jetbrains.annotations.Nullable()
    private java.util.Date _date;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer get_id() {
        return null;
    }
    
    public final void set_id(@org.jetbrains.annotations.Nullable()
    java.lang.Integer p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String get_type() {
        return null;
    }
    
    public final void set_type(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date get_date() {
        return null;
    }
    
    public final void set_date(@org.jetbrains.annotations.Nullable()
    java.util.Date p0) {
    }
    
    public Base(@org.jetbrains.annotations.Nullable()
    java.lang.Integer _id, @org.jetbrains.annotations.Nullable()
    java.lang.String _type, @org.jetbrains.annotations.Nullable()
    java.util.Date _date) {
        super();
    }
    
    public Base() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Base$TYPE;", "", "()V", "Companion", "app_debug"})
    public static final class TYPE {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String WEATHER = "weather";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String DETAILS = "details";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String GRAPH = "graph";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String MAP = "map";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ADS = "ads";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String DAYS = "days";
        public static final com.craiovadata.android.sunshine.ui.models.Base.TYPE.Companion Companion = null;
        
        public TYPE() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/craiovadata/android/sunshine/ui/models/Base$TYPE$Companion;", "", "()V", "ADS", "", "DAYS", "DETAILS", "GRAPH", "MAP", "WEATHER", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
        }
    }
}