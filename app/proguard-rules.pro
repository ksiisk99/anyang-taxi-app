# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings
-obfuscationdictionary "C:\SDK\Android\Sdk\tools\proguard\dic.txt"
-classobfuscationdictionary "C:\SDK\Android\Sdk\tools\proguard\dic.txt"
-packageobfuscationdictionary "C:\SDK\Android\Sdk\tools\proguard\dic.txt"
-mergeinterfacesaggressively
-overloadaggressively
-repackageclasses 'com.example.newchat'

-dontwarn java.annotation.**

-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okhttp3.**

-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8

-keep class com.google.gson.examples.android.model.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keep interface com.ay.newchat.i2** {*;}
-keep class com.ay.newchat.ri1** {*;}
-keep class com.ay.newchat.ri2** {*;}
-keep class com.ay.newchat.ri3** {*;}
-keep class com.ay.newchat.ri4** {*;}
-keep class com.ay.newchat.ri5** {*;}
-keep class com.ay.newchat.ri6** {*;}
-keep class com.ay.newchat.p1** {*;}
-keep class com.ay.newchat.p2** {*;}
-keep class com.ay.newchat.p3** {*;}
-keep class com.ay.newchat.p4** {*;}
-keep class com.ay.newchat.p5** {*;}
-keep class com.ay.newchat.p6** {*;}
-keep class com.ay.newchat.p7** {*;}
-keep class com.ay.newchat.p8** {*;}
-keep class com.ay.newchat.p9** {*;}
-keep class com.ay.newchat.ac** {*;}

