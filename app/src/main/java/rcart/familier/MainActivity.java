package rcart.familier;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton boutonManger;
    ImageButton boutonSports;
    ImageButton boutonSommeil;
    ImageButton boutonOptions;
    ImageView imageMonstre;

    boolean boolEtatPartie;

    String nomMonstre;
    int lvlMonstre, experienceActuelle, experienceMax,enduranceActuelle, enduranceMax, vieActuelle, vieMax;
    TextView labelIdentifiant, labelExperience, labelVie, labelEndurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelIdentifiant = (TextView) findViewById(R.id.textIdentifiant);
        labelExperience = (TextView) findViewById(R.id.testExperience);
        labelVie = (TextView) findViewById(R.id.textVie);
        labelEndurance = (TextView) findViewById(R.id.textEndurance);

        // On initialise le niveau initial du monstre et on le définit
        lvlMonstre = 1;
        nomMonstre = "Yéti";
        boolEtatPartie = true;
        imageMonstre =(ImageView) findViewById(R.id.imgMonstre);


        updateCaracteristiquesMax();
        boutonManger = (ImageButton) findViewById(R.id.btnManger); // Bouton pour nourrir le monstre, lui redonne des points de vie
        boutonManger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (vieActuelle<vieMax){
                    if (vieActuelle <= (vieMax-5)){
                        vieActuelle = vieActuelle+5;
                    }
                    else {
                        vieActuelle = vieActuelle + (vieMax - vieActuelle);
                    }
                }
                updateLabelText(boolEtatPartie);
            }
        });

        boutonSports = (ImageButton) findViewById(R.id.btnSport); // Bouton pour faire faire du sport au monstre, lui retire de la vie et de l'endurance, mais fait gagner de l'expérience
        boutonSports.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (enduranceActuelle > 0 ){
                    if (enduranceActuelle >= 10){
                        enduranceActuelle = enduranceActuelle - 10;
                    }
                    else {
                        enduranceActuelle = 0;
                    }

                    if (vieActuelle > 10){
                        vieActuelle = vieActuelle - 10;
                        experienceActuelle = experienceActuelle + 10;
                        updateLvl();
                        updateLabelText(boolEtatPartie);
                    }
                    else {
                        vieActuelle = 0;
                        verificationVie();

                    }
                }

            }
        });

        boutonSommeil = (ImageButton) findViewById(R.id.btnSommeil); // Bouton pour faire dormir le monstre, lui régénère son endurance
        boutonSommeil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (enduranceActuelle<enduranceMax){
                    if (enduranceActuelle <= (enduranceMax-25)){
                        enduranceActuelle = enduranceActuelle+25;
                    }
                    else {
                        enduranceActuelle = enduranceActuelle + (enduranceMax - enduranceActuelle);
                    }
                }
                updateLabelText(boolEtatPartie);
            }
        });

        boutonOptions = (ImageButton) findViewById(R.id.btnOptions); // Bouton pour le panel d'options
        boutonOptions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (boolEtatPartie == false){
                    lvlMonstre = 1;
                    nomMonstre = "Yéti";
                    boolEtatPartie = true;
                    updateLabelText(boolEtatPartie);
                    imageMonstre.setImageResource(R.drawable.monsterunhappy);

                }
            }
        });
    }

    protected  void verificationVie () { // Vérifie si le nombre de points de vie n'est pas à 0, sinon mort

        if (vieActuelle <=0 ){

            boolEtatPartie = false;
            updateLabelText(boolEtatPartie);
        }
    }


    protected void updateLvl() { // Permet de savoir si il faut augmenter le niveau du monstre

        if(experienceActuelle >= experienceMax){
            lvlMonstre = lvlMonstre +1;
            updateCaracteristiquesMax();
            imageMonstre.setImageResource(R.drawable.monsterhappy);
        }
    }

    protected void updateLabelText (boolean bool) { // Met à jour l'ensemble de l'interface
        // Le booléen permet de savoir si (true) la partie est en cours, ou si (false) la partie est terminée
        if (bool == true){

            labelIdentifiant.setText(nomMonstre+" lvl "+lvlMonstre);
            labelExperience.setText(experienceActuelle+"/"+experienceMax+" points d'expériences");
            labelVie.setText(vieActuelle+"/"+vieMax+" points de vie");
            labelEndurance.setText(enduranceActuelle+"/"+enduranceMax+" points d'endurance");

        } else if (bool == false){

            labelIdentifiant.setText("");
            labelExperience.setText("VOUS AVEZ PERDU, APPUYEZ SUR PARAMETRES (en bas à droite) POUR RECOMMENCER");
            labelVie.setText("");
            labelEndurance.setText("");

        }

    }

    protected void updateCaracteristiquesMax () { // Met à jour en fonction du lvl du monstre l'ensemble des caractéristiques

        experienceMax = lvlMonstre * 200;
        enduranceMax = (lvlMonstre * 200)/2;
        vieMax = (lvlMonstre * 50);
        experienceActuelle = 0;
        vieActuelle = vieMax;
        enduranceActuelle = enduranceMax;
    }
}
