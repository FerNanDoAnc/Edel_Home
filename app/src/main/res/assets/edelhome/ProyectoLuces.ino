char c;
char estado;
char id;
char dato;

// LEDS
int led1 = 12;
boolean isSecondParam = false;
void setup(){
  Serial.begin(9600);
  pinMode(12, OUTPUT);
}

void loop(){
  if (Serial.available() > 0){
    dato = Serial.read();

    if(isSecondParam){
       estado = dato;
     };

    if(dato == ','){
      isSecondParam = true;
    };

    if(!isSecondParam){
       id = dato;
    };


     if(id == '1'){
        if(estado == '1'){
            digitalWrite(led1,HIGH);
          }
        if(estado == '0'){
            digitalWrite(led1, LOW);
        }
      }
  Serial.println(dato);

  }
  Serial.println("id"+id);
}