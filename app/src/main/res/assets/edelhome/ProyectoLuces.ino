#include <Separador.h>
Separador s;
char c;
String estado;
String id;
void setup()
{
  Serial.begin(9600);
  pinMode(12, OUTPUT);
}

void loop(){
  if (Serial.available() > 0){
    String datosrecibidos=Serial.readString();

    estado = s.separa(datosrecibidos,',',0);
    id = s.separa(datosrecibidos,',',1);
    Serial.println("Error de datos"+estado+id);

    if(id == "1"){
      Serial.println("Error Shimiastico");

      if (estado == "true"){
      Serial.println("on");
      digitalWrite(12, HIGH);
      }else{
        Serial.println("off");
        digitalWrite(12, LOW);
      }
   }else {
    Serial.println("oyopiz");
    }
 }else{
    delay(100);
  }
  
}