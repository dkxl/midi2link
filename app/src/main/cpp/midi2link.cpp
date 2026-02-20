#include <jni.h>
#include <string>
#include <stdexcept>
#include <iostream>
#include "AbletonLinkSession.h"

extern "C" {


// Helper to throw a specific Java exception from C++
void throwJavaException(JNIEnv* env, const char* exceptionClassName, const char* message) {
    jclass exClass = env->FindClass(exceptionClassName);
    if (exClass != nullptr) {
        env->ThrowNew(exClass, message);
    }
}

// jobject is a reference to the calling Java object
JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_ableton_Link_createLinkSession(JNIEnv *env, jobject me, jdouble bpm, jboolean bEnable) {
    try {
        auto *pLinkSession = new AbletonLinkSession(bpm, bEnable);
        auto returnHandle = (jlong) pLinkSession;
        return returnHandle;
    } catch (const std::exception &e) {
        // Catch standard C++ exceptions and translate to Java
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return 0;  // must still specify a return value unless return type is void
    } catch (...) {
        // Catch-all for non-standard C++ errors
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return 0;
    }
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getTempo(JNIEnv *env, jobject me, jlong handle) {
    try {
        auto *pLinkSession = (AbletonLinkSession *) handle;
        return pLinkSession->getTempo();
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return -1;
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return -1;
    }
}

JNIEXPORT jdouble JNICALL
Java_com_dkxl_midi2link_ableton_Link_getPhase(JNIEnv *env, jobject me, jlong handle, jdouble quantum) {
try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    return pLinkSession->getPhase(quantum);
} catch (const std::exception &e) {
    throwJavaException(env, "java/lang/RuntimeException", e.what());
    return -1;
} catch (...) {
    throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
    return -1;
}
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setEnabled(JNIEnv *env, jobject me, jlong handle, jboolean bEnable) {
try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    pLinkSession->setEnabled(bEnable);
}  catch (const std::exception &e) {
    throwJavaException(env, "java/lang/RuntimeException", e.what());
} catch (...) {
    throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
}
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setTempo(JNIEnv *env, jobject me, jlong handle, jdouble bpm) {
    try {
        auto *pLinkSession = (AbletonLinkSession *) handle;
        pLinkSession->setTempo(bpm);
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_dkxl_midi2link_ableton_Link_isEnabled(JNIEnv *env, jobject me, jlong handle) {
    try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    return (pLinkSession != nullptr) && pLinkSession->isEnabled();
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return false;
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return false;
    }
}

JNIEXPORT void JNICALL
Java_com_dkxl_midi2link_ableton_Link_setPlaying(JNIEnv *env, jobject me, jlong handle, jboolean bPlay) {
    try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    pLinkSession->setPlaying(bPlay);
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_dkxl_midi2link_ableton_Link_isPlaying(JNIEnv *env, jobject me, jlong handle) {
    try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    return (pLinkSession != nullptr) && pLinkSession->isPlaying();
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return false;
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return false;
    }
}

JNIEXPORT jlong JNICALL
Java_com_dkxl_midi2link_ableton_Link_numPeers(JNIEnv *env, jobject me, jlong handle) {
    try {
    auto *pLinkSession = (AbletonLinkSession *) handle;
    return pLinkSession->numPeers();
    } catch (const std::exception &e) {
        throwJavaException(env, "java/lang/RuntimeException", e.what());
        return -1;
    } catch (...) {
        throwJavaException(env, "java/lang/Error", "Unknown C++ exception occurred");
        return -1;
    }
}

} // extern