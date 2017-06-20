int luminosita = 0;
int pinLed = 5;
int pinSensore = 0;
int pirPin = 2;
int pinSpeaker = 3;
int pinRele = 4;
int ledPin = 13;
const int sensorpin = 3; // il DO del sensore e’ collegato alla porta 3
const int ledpin = 9; // il led e’ collegato alla porta 9
const int buzzerpin = 11; // il buzzer e’ collegato alla porta 11;

void setup() {
  Serial.begin(9600);
  pinMode(pirPin, INPUT);
  pinMode(pinSpeaker, OUTPUT);
  pinMode (pinRele, OUTPUT);
  digitalWrite(pinRele, HIGH);
  pinMode(ledPin, OUTPUT);
  Serial.println("Programma allarme con PIR");
  Serial.println("Pausa stabilizzazione sensore");
  tone(pinSpeaker, 1000, 500); //tone(pin, frequency, duration)
  delay (5000);
  Serial.println("Inizio controllo");
  tone(pinSpeaker, 500, 500);
  pinMode(pinLed, OUTPUT);
  pinMode(pinSensore, INPUT);
  pinMode(sensorpin, INPUT);
  pinMode(ledpin, OUTPUT);
  pinMode(buzzerpin, OUTPUT);
}

void loop() {
  //Allarme
  int pirVal = digitalRead(pirPin);
  Serial.println("Lettura sensore PIR");
  analogWrite(ledPin, 0);
  digitalWrite(pinRele, HIGH);
  delay(2000);
  if (pirVal == HIGH) {
    Serial.println("RILEVATO MOVIMENTO");
    analogWrite(ledPin, 255);
    digitalWrite(pinRele, LOW);
    tone(pinSpeaker, 1000, 1000);
    delay(2000);
  }

  //Sensore gas
  float sensor_volt;
  float RS_gas; // Prende il valore RS dal sensore GAS
  float ratio;
  int sensorValue = analogRead(A0);
  sensor_volt = (float)sensorValue / 1024 * 5.0;
  RS_gas = (5.0 - sensor_volt) / sensor_volt;
  ratio = RS_gas / R0;
  Serial.print("sensor_volt = ");
  Serial.println(sensor_volt);
  Serial.print("RS_ratio = ");
  Serial.println(RS_gas);
  Serial.print("Rs/R0 = ");
  Serial.println(ratio);
  Serial.print("\n\n");
  delay(1000);

  //Sensore pioggia
  int sensorValue = analogRead(A0); //Legge il valore analogico
  Serial.println(sensorValue); //Stampa a schermo il valore
  delay(2000); //Attende due secondi

  //Sensore temperatura
  int soiatemp = map (analogRead (TermoTrim), 0, 1023, 0, 40);
  if (bluerx == 'R')
  {
    Serial.print("Il riscaldamento si accende a ");
    Serial.print(soiatemp);
    Serial.println("°C");
    bluerx = 'r';
  }
  for ( cont = 0; cont < 10; cont++)
  {
    sensoreTemperatura = analogRead(LM35_TEMP); // lettura della tensione del pin OUT LM35
    sensoreTemperatura_medio += sensoreTemperatura; //sommatoria dei valori
    ref = analogRead(LM35_REF); // lettura della tensione di riferimento
    ref_medio += ref; // sommatoria dei valori
    delay(50); // intervallo di campionamento
    //la lettura durera' 10 (numero di campioni) x 50ms (intervallo tra due campioni) = 500ms
  }
  if (cont == 10) // quando ho sommato i dieci valori campionati si esegue:
  {
    cont = 0; // azzeramento contatore, per far ripartire il successivo campionamento
    // media della sommatoria dei dieci valori campionati di ref e sensoreTemperatura
    sensoreTemperatura_medio = sensoreTemperatura_medio / 10;
    ref_medio = ref_medio / 10;
    // conversione dei valori medi misurati in una temperatura in gradi centigradi
    temperatura = (sensoreTemperatura_medio - ref_medio) * 100 / 2.56;
    temperatura = temperatura * 5 / 1024;
    // valore di temperatura che verra' mostrato quando si e' in fase di campionamento
    //e non c'e' una temperatura disponibile
    prev_temperatura = temperatura;
    // prima di un successiva acquisizione e media questi valori vanno azzerati
    sensoreTemperatura_medio = 0;
    ref_medio = 0;
  }
  else
  {
    temperatura = prev_temperatura;
  }
  temperatura = temperatura - 7;
  26
  if (temperatura < soiatemp)
    // if (temperatura < 20)
  {
    digitalWrite(ledPintemp, HIGH);
  } else {
    digitalWrite(ledPintemp, LOW);
  }
  if (bluerx == 'T')
  {
    Serial.print("La temperatura è di ");
    Serial.print(temperatura);
    Serial.println("°C");
    bluerx = 't';
  }

  //Sensore Luce
  luminosita = analogRead (0);
  if (sensore < 500) {
    digitalWrite(pinLed, HIGH);
  }
  else {
    digitalWrite(pinLed, LOW);
  }

  //Sensore fiamma
  statosensore = digitalRead(sensorpin); //rileva lo stato del sensore
  if (statosensore == HIGH) // se e’ presente una fiamma
  {
    digitalWrite(ledpin, HIGH); // attiva l’allarme
    digitalWrite(buzzerpin, HIGH);
    delay (300); // attende 3/10 di secondo
  }
  digitalWrite(ledpin, LOW); // spegne l’allarme
  digitalWrite(buzzerpin, LOW);
  delay (300); // attende 3/10 di secondo e verifica nuovamente lo stato del sensore
}
