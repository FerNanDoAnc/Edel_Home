int led = 2;
int led1 = 3;
int led2 = 4;
char dato;

void setup() {
  pinMode(led, OUTPUT);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if (Serial.available() > 0) {
    dato = Serial.read();

  if(dato == '1'){
      digitalWrite(led, HIGH);
  }
  if (dato == '0') {
      digitalWrite(led, LOW);
  }
    if (dato == '3') {
      digitalWrite(led1, HIGH);
  }
    if (dato == '2') {
      digitalWrite(led1, LOW);
  }
    if (dato == '5') {
      digitalWrite(led2, HIGH);
  }
    if (dato == '4') {
      digitalWrite(led2, LOW);
  }

}
}
