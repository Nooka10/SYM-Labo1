# SYM_Labo1 - Réponses aux questions

Auteurs: J.Châtillon, A.Rochat, B.Schopfer
Date: 01.10.2018
Template du rapport: J.Smith

## Question 1

```
Comment organiser les textes pour obtenir une application multi-langues (français, allemand, italien, langue par défaut : anglais) ?
Que se passe-t-il si une traduction est manquante dans la langue par défaut ou dans une langue supplémentaire ?
```

Il ne faut jamais coder en dur du texte.  Lorsqu'on souhaite afficher du texte dans l'application, il faut aller récupérer des textes dans le fichier *strings.xml* de la langue souhaitée.

Pour la langue par défaut, il faut créer un fichier *strings.xml* dans le dossier *./res/values/*. Pour les autres langues, il faut créer le même fichier mais dans un autres dossier *./res/values-XX/* ou XX représente le code (sur 2 caractères) du language. Il est aussi possible de préciser la région ou d'utiliser un "BCP 47 language tag". (cf: https://developer.android.com/guide/topics/resources/providing-resources#AlternativeResources)
Le fichier string.xml doit avoir la structure:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="refExemple">Text exmple</string>
</resources>
```

On récupère la valeur depuis l'application en utilisant l'instruction:

```java
getResources().getString(R.string.refExemple); // depuis le java
```

```xml
 <TextView android:text="@string/refExemple" /> 	// depuis l'xml
```

Si une traduction est manquante dans une langue supplémentaire, alors le texte de la langue par défaut sera affichée. En revanche, le fichier *string.xml* de la langue par défaut doit être complet sans quoi l'application ne pourra pas compiler. 

## Question 2

```
Dans l’exemple fourni, sur le dialogue pop-up, nous affichons l’icône android. R.drawable.ic_dialog_alert , disponible dans le SDK Android mais qui n’est pas très bien adapté visuellement à notre utilisation. Nous souhaitons la remplacer avec notre propre icône, veuillez indiquer comment procéder. Dans quel(s) dossier(s) devons-nous ajouter cette image ? Décrivez brièvement la logique derrière la gestion des ressources de type « image » sur Android.
```

Il faut ajouter l'icone que nous souhaitons utiliser dans le dossier *res/drawable*. Après cela, on remplace la ligne 

```
alertbd.setIcon(android.R.drawable.ic_dialog_alert);
```

par

```
alertbd.setIcon(R.drawable.ic_warning_black_24dp);
```

où *ic_warning_black_24dp* est le nom de l'icone.

Dans Android Studio, le moyen le plus facile d'ajouter une icône est d'utiliser le menu File -> New -> Vector Asset. Le menu qui s'ouvre permet de choisir une icône personnalisée (à donner au format SVG ou PSD) ou de choisir une icône open source (*Clip ARt*) dans le style *Material Design*.

Dans le dossier dra

Pour les images par défaut, il faut créer le dossier *./res/drawable/* et mettre l'image dedans. Puis il faudrait créer un dossier pour chacune des résolutions avec cette même image.

![image-ressources](C:\Users\basilechatillon\Documents\jeremie\SYM\Labos\SYM-Labo1\image-ressources.jpg)

On récupère l'image avec l'instruction:

```java
File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + "/perso.jpg");
```

PAS FINI!!

## Question 3

```
Lorsque le login est réussi, vous êtes censé chaîner une autre Activity en utilisant un Intent. Si je presse le bouton "Back" de l'interface Android, que puis-je constater ? Comment faire pour que l'application se comporte de manière plus logique ? Veuillez discuter de la logique derrière les activités Android.
```

Appuyer sur le bouton back "quitte" l'application (retour à l'écran d'accueil). La logique voudrait qu'appuyer sur retour nous déconnecte et nous ramène à la page de login.

https://developer.android.com/training/implementing-navigation/temporal

```java
@Override
public void onBackPressed() {
    //super.onBackPressed();
    Intent i = new Intent();
    finish();
}
```

## Question 4

```
On pourrait imaginer une situation où cette seconde Activity fournit un résultat (par exemple l’IMEI ou une autre chaîne de caractères) que nous voudrions récupérer dans l'Activity de départ. Comment procéder ?
```

```java
@Override
public void onBackPressed() {
    //super.onBackPressed();
    Intent i = new Intent();
    i.putExtra(EXTRA_NON_DOWNLOADED_PAGES, notDownloaded);
    setResult(RESULT_OK, i);
    finish();
}
```



## Question 5

```
Vous noterez que la méthode getDeviceId() du TelephonyManager, permettant d’obtenir l’IMEI du téléphone, est dépréciée depuis la version 26 de l’API. Veuillez discuter de ce que cela implique lors du développement et de présenter une façon d’en tenir compte avec un exemple de code.
```

Car elle ne fait pas la difference entre un device GSM ou CDMA. Il faut coder une version pour les 2.

Si on veut le GSM id(IMEI), il faut utiliser *getImei()*:

```java
 @RequiresApi(api = Build.VERSION_CODES.O)
 TelephonyManager tm = (TelephonyManager)
            getSystemService(this.TELEPHONY_SERVICE);
    String imei = tm.getImei();
```

Et si on veut CDMA id (MEID), il faut utiliser *getMeid()*:

```java
@RequiresApi(api = Build.VERSION_CODES.O)
TelephonyManager tm = (TelephonyManager)
            getSystemService(this.TELEPHONY_SERVICE);

    String meid=tm.getMeid();
```

## Question 6

```
Dans l’activité de login, en plaçant le téléphone (ou l’émulateur) en mode paysage (landscape), nous constatons que les 2 champs de saisie ainsi que le bouton s’étendent sur toute la largeur de l’écran. Veuillez réaliser un layout spécifique au mode paysage qui permet un affichage mieux adapté et indiquer comment faire pour qu’il soit utilisé automatiquement à l’exécution.
```



## Question 7

```
Le layout de l’interface utilisateur de l’activité de login qui vous a été fourni a été réalisé avec un LinearLayout à la racine. Nous vous demandons de réaliser un layout  équivalent utilisant cette fois-ci un RelativeLayout.
```



## Question 8

```
Implémenter dans votre code les méthodes onCreate(), onStart(), onResume(), onPause(), onStop(), etc... qui marquent le cycle de vie d'une application Android, et tracez leur exécution dans le logcat. Décrivez brièvement à quelles occasions ces méthodes sont invoquées. Si vous aviez (par exemple) une connexion Bluetooth (ou des connexions bases de données, ou des capteurs activés) ouverte dans votre Activity, que faudrait-il peut-être faire, à votre avis (nous ne vous demandons pas de code ici) ?
```



## Question 9

```
Facultatif – Question Bonus - S’il vous reste du temps, nous vous conseillons de le consacrer à mettre en place la résolution des permissions au runtime.
```



### To do

- [ ] Ajouter champs
  - [ ] Email
  - [ ] Mdp
  - [ ] Bouton de validation
- [ ] Controle de l'email 
  - [ ] doit contenur le signe @
  - [ ] Mettre un toast en cas d'erreur indicant qu'il est nécessaire de mettre un email valdie
- [ ] Vérification de l'existance de l'email
  - [ ] Créer en dur des pairs mdp et email
  - [ ] Vérifier le mdp et email
  - [ ] Afficher une pop-up "Utilisateur ou mot de passe inconnu" en cas d'erreur. Utiliser un DialogBuilder
  - [ ] Sur quittance de l'utilisateur (appuie sur un bouton) revenir à l'application en effacant préalablement les entrées précédentes de l'utilisateur. 
  - [ ] Le message affiché devra être extrait du fichier XML `res/values/strings.xml`
- [ ] Lancer une activité séparée permettant d'afficher l'e-mail introduit précédemment, l'IMEI du terminal (pour l'émulateur, ce sera "000000..." ou même null sur les versions d’Android 8.0 2 ) ainsi qu'une photographie, censée représenter l'utilisateur qui vient de réussir son login, résidente sur la carte SD (utilisez par exemple : Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOWNLOADS); pour accéder au dossier des téléchargements).