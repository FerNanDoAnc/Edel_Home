int led2=5;
int led3=6;
int led4=7;
char foco;
char dato;

void setup() {
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  pinMode(led4,OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if(Serial.available()>0){
    dato=Serial.read();
  if(dato=='1'){
         if (dato=='1'){  
           digitalWrite(led2,HIGH);}
         if(dato=='0'){
            digitalWrite(led2,LOW);}
              }
  if(dato=='2'){
        if (dato=='1'){  
           digitalWrite(led2,HIGH);}
        if(dato=='0'){
            digitalWrite(led2,LOW);}
              }
  
  }

}
