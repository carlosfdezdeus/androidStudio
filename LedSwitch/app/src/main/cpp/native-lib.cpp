#include <jni.h>
#include <string>
#include <iostream>
#include <fstream>  // Para entrar en el fichero

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_example_ledswitch_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}




const std::string USR3_BRIGHTNESS = "/sys/class/leds/beaglebone:green:usr3/brightness";

/* Devuelve el valor que tiene el fichero brightness*/
extern "C" JNIEXPORT jint JNICALL
Java_com_example_ledswitch_MainActivity_ledstatus(JNIEnv* env, jobject /* this */){
    // Variable para almacenar el número entero leído del archivo
    int val = 0;
    // Crear un objeto ifstream para leer el archivo
    std::ifstream archivoEntrada(USR3_BRIGHTNESS);

    // Verificar si el archivo se abrió correctamente
    if (!archivoEntrada.is_open()) {
        std::cerr << "Error al abrir el archivo " << USR3_BRIGHTNESS << std::endl;
        return 1; // Terminar el programa con un código de error
    }

    // Leer el contenido del archivo (solo la primera línea) y convertirlo a un número entero
    std::string brightnessValue;
    if (std::getline(archivoEntrada, brightnessValue)) {
        try {
            val = std::stoi(brightnessValue);
        } catch (const std::invalid_argument& e) {
            // Manejar la excepción si la conversión falla
            std::cerr << "Error al convertir a entero en la línea: " << brightnessValue << std::endl;
        }
    }

    return val;
}

/* Cambia el valor del fichero brigthness al parametro val */
extern "C" JNIEXPORT jint JNICALL
Java_com_example_ledswitch_MainActivity_writeled(JNIEnv* env, jobject /* this */, int val){
    std::ofstream archivoSalida(USR3_BRIGHTNESS);

    if (!archivoSalida.is_open()) {
        std::cerr << "Error al abrir el archivo " << USR3_BRIGHTNESS << " para escritura" << std::endl;
        return 1;
    }

    archivoSalida << val;
    archivoSalida.close();

    return 0;
}