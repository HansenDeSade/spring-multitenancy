# Mandantenfähigkeit
Dieses Projekt ist eine Art Prototyp bzw. Proof of concept, um in Erfahrung zu bringen, wie eine Mandantenfähigkeit bei einem Microservice erreicht werden kann. Bei Mandantenfähigkeit (engl. multitenancy) sind 3 Abstufungen möglich:
* Kunden-Kennung an jedem Datensatz - geteilte Datenbank
* geteilte Datenbank, aber ein Schema pro Kunde
* jeder Kunde hat seine eigene Datenbank
Dieser Prototyp verfolgt den Ansatz der getrennten Datenbanken, damit ein möglichst hoher Isolationsgrad erlangt wird. Oft wird davon gesprochen, dass der hohe Grad an Datenisolation durch Performance gekauft wird. Auch dies sollte durch diesen Prototypen untersucht werden.

# Grobe Idee
Der Service hat nur einen beispielhaften Nutzen: Per GET kann eine Liste aller externen Systeme (ID + Name) zurückgegeben werden. Angeschlossen sind zur Zeit die Puma/SCHNEIDER, Puma/POPKEN und localhost Datenbank. Unterschieden, welche DB nun angesprochen werden muss, wird durch den JWT-Token, der vorher per /authenticate bezogen wird. Beim Authentifizieren muss als Header eine "tenantid" übergeben werden (z.Zt. "schneider", "popken" und "localhost"). Diese Kundenkennung ist dann auch Teil des JWT-Tokens. Vor jeder Ausführung eines Requests wird dann per Interceptor die Kundenkennung aus dem JWT-Token geholt und die DB-Connection umgestellt.

# Beispiele

Authentifizierung:
Um es einfach zu halten, gibt es zur Zeit nur "userA" mit dem Passwort "test".
`curl -X POST \
  http://localhost:8080/authenticate \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'tenantid: schneider' \
  -d '{
	"username": "userA",
	"password": "test"
}'`

Request mit JWT-Token:
Der Token hat immer nur eine Haltbarkeit von einem Tag und wird deswegen nicht mehr funktionieren wie in diesem Beispiel.
`curl -X GET \
  http://localhost:8080/externalsystem \
  -H 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQSIsImV4cCI6MTUxNDYyODM0NiwiaXNzIjoiTXVsdGl0ZW5hbmN5IG51bGwiLCJpYXQiOjE1MTQ1NDE5NDYsImF1ZCI6InNjaG5laWRlciJ9.Cd9WJPRdUXcO5jT_Pp75ejZK_OHlZsFM7FGM33LbTxNHILbxi2a6W5I6xGBQ_6C7AvWiLE6W8cILX_2yPEn7AA' \
  -H 'cache-control: no-cache' \`