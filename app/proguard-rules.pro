# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep generic signatures for reflection
-keepattributes Signature

# Keep annotations
-keepattributes *Annotation*

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.arch.template.**$$serializer { *; }
-keepclassmembers class com.arch.template.** {
    *** Companion;
}
-keepclasseswithmembers class com.arch.template.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep data classes used for serialization
-keep @kotlinx.serialization.Serializable class * {
    *;
}

# Koin
-keep class org.koin.** { *; }
-keep class * { public <init>(...); }
-keepclassmembers class ** {
    @org.koin.core.annotation.* *;
}

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Keep model classes
-keep class com.arch.template.data.model.** { *; }
-keep class com.arch.template.data.remote.dto.** { *; }