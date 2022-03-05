package study.coco.Game;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import study.coco.Game.Describable.Gate;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;
import study.coco.Game.Handler.*;
import study.coco.Game.Handler.CommandHandler.*;
import study.coco.Game.Handler.CommandHandler.ItemHandler.DropHandler;
import study.coco.Game.Handler.CommandHandler.ItemHandler.UseHandler;
import study.coco.Game.Handler.CommandHandler.MoveHandler;
import study.coco.Game.Handler.CommandHandler.ItemHandler.TakeHandler;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private Terminal terminal;
    private LineReader reader;

    // little history of past handlers for debugging
    private int handleCount;
    public String handleTypes = "";

    // storing the current terminal input
    private String lineInput;
    private String[] commandParts;

    // some message presets
    private String inputCall = " >> ";
    private String inputMsg = "\nWas soll ich als nächstes tun?";
    private String gameHeadline = """
            
            █▀▄▀█ ▀▄    ▄  ▄▄▄▄▄      ▄▄▄▄▀ ▄███▄   █▄▄▄▄ ▀▄    ▄\s
            █ █ █   █  █  █     ▀▄ ▀▀▀ █    █▀   ▀  █  ▄▀   █  █ \s
            █ ▄ █    ▀█ ▄  ▀▀▀▀▄       █    ██▄▄    █▀▀▌     ▀█  \s
            █   █    █   ▀▄▄▄▄▀       █     █▄   ▄▀ █  █     █   \s
               █   ▄▀                ▀      ▀███▀     █    ▄▀    \s
              ▀  ▄▀  ██   █▄▄▄▄ ██▄   ▄███▄      ▄   ▀           \s
               ▄▀    █ █  █  ▄▀ █  █  █▀   ▀      █                 \s
               █ ▀▄  █▄▄█ █▀▀▌  █   █ ██▄▄    ██   █                \s
               █   █ █  █ █  █  █  █  █▄   ▄▀ █ █  █                \s
                ███     █   █   ███▀  ▀███▀   █  █ █                \s
                       █   ▀                  █   ██                \s
                      ▀                                             \s
            Ein TEXT ADVENTURE von Henning Brode © 2022
            """;
    private String introMsg = """
            • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • •
            • -> Mit dem Kommando 'help' erhältst du einige hilfreiche Tipps.   •
            • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • • •
                    
            |~ EINLEITUNG:
            | Die Straße ist eng und kurvig, schon seit 15 Minuten fällt kaum Licht auf die
            | Fahrbahn. An den Straßenrändern ragen die hohen Tannen in den Himmel und in mir
            | kommt das Urlaubsgefühl hoch, wie damals, als ich bei meinen Großeltern abgesetzt 
            | wurde, kurz bevor meine Eltern weiter Richtung Süden fuhren. Das letzte Mal, dass
            | wir alle gemeinsam hier waren muss über sieben Jahre her sein. Seitdem hatte ich 
            | mich davor gedrückt, das leere alleinstehende Haus, das seit geraumer Zeit mir 
            | gehört, zu betreten.
            | Dabei hatte da dieser Brief auf meinem Kopfkissen gelegen, kurz nachdem sie mir 
            | sagten, Oma habe mir das alte Häuschen im Wald vermacht. Eine Nachricht, die mich 
            | sprachlos hinterließ. Ich dachte, es sei eine Erklärung für diese Entscheidung, 
            | hatte ich doch immer auch ein wenig Angst gehabt vor dem Holzhaus, dessen meisten 
            | Kellertüren stets geschlossen waren. Stattdessen lag in dem Umschlag nur ein rostiger
            | mit den Blüten des Fingerhutes verzierter Schlüssel und eine kurze Notiz.
            | Mit beiden Gegenständen habe ich mich nun also auf den weiten Weg in den tiefen Wald
            | gemacht, um die Antworten auf meine Fragen zu finden.
                 
            Ich parke das alte Auto vor dem Haus, stelle den Motor ab und betrete den %s.
                   
            Ich schaue mich um:""";
    private String solvedGameMsg = """
            Als ich das kleine Fläschchen öffne, strömt die silbrig schimmernde Flüssigkeit hinaus und 
            vermischt sich mit der Flüssigkeit, die sich in der vor mir schwebenden Schale befindet. 
            Es zischt und ein helles Licht erstrahlt. Plötzlich kann ich meine Großeltern erkennen. 
            Ich tauche mein Gesicht in die Schale und kann nun die Stimme meiner Oma hören:
                    
            'Mein Liebling,
            bestimmt hast du nun mehr Fragen als vorher.
                    
            Unsere Familie entstammt einer alten Hexenlinie und auch in dir ströhmt magisches Blut.
            Wenn du dein Herz und deinen Blick geöffnet lässt, wirst du alle Antworten finden.
                    
            Komm hierher zurück, wann immer du einen Rat suchst.
                    
            Wir haben dich lieb!'
                    
            Währenddessen sehe ich, wie Oma und Opa die Pflanzen auf den Beeten pflegen, ohne sie mit den 
            Händen zu berühren.
                    
            * • ~ • * • ~ • * • - ~ - • * • ~ • * • ~ • *
                    
                   ▄███▄      ▄   ██▄   ▄███▄  \s
                    █▀   ▀      █  █  █  █▀   ▀ \s
                    ██▄▄    ██   █ █   █ ██▄▄   \s
                    █▄   ▄▀ █ █  █ █  █  █▄   ▄▀\s
                    ▀███▀   █  █ █ ███▀  ▀███▀  \s
                            █   ██              \s
                                            \s
            * • ~ • * • ~ • * • - ~ - • * • ~ • * • ~ • *            
                    
                | Herzlichen Glückwunsch!           |
                | Du hast alle Rätsel gelöst.       |
                |                                   |
                | Ich hoffe, du hattest viel Spaß!  |
                |                                   |
                | Bis bald,                         |
                |       Henning!                    |
                |                                   |
                |                                   |
                | © 2022                            |
                | Kontakt: hello@henningbrode.de    |
            
            """;

    // list of available input handlers
    private ArrayList<Handler> handlers = new ArrayList<>();

    // init an instance of the error handler
    Handler errorHandler = new ErrorHandler(this);

    // there is always a current handler for handling the terminal input
    Handler currentHandler;

    // init player
    private Player player;

    // init final items
    private Location finalLocation;
    private Item finalKey;

    // init game states
    private boolean finished;
    private boolean solved;


    public Game() throws IOException {
        // init terminal & input instances
        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        lineInput = "";

        // init & add all necessary game handlers to the handler list
        this.handlers.add(new ExitHandler(this));
        this.handlers.add(new MoveHandler(this));
        this.handlers.add(new TakeHandler(this));
        this.handlers.add(new DropHandler(this));
        this.handlers.add(new UseHandler(this));
        this.handlers.add(new InventoryHandler(this));
        this.handlers.add(new LookHandler(this));

        // INIT ALL ITEMS
        Item brief = new Item("Brief", """
Der Brief der einst zusammen mit dem rostigen alten Schlüssel auf meinem Kopfkissen lag.\n\n
Darin steht:
Willst du öffnen jeden Ort,
nimm des dritten Verses fünftes Wort.""");
        Item k1 = new Item("Hausschlüssel", """
Ein rostiger alter Schlüssel für Oma und Opas Waldhäuschen.""");
        Item k2 = new Item("Brecheisen", """
Ein altes rostiges Brecheisen. Hiermit kann man bestimmt gut Dinge aufhebeln.""");
        Item gedicht = new Item("Gedicht", """
Das Gedicht 'Fingerhütchen' von Conrad Ferdinand Meyer ist auf einem mit Fingerhüten
verzierten Pergament geschrieben:
• • • • • • • • • • • • • • • • • • •
• FINGERHÜTCHEN                     •
• -------------                     •
• Liebe Kinder, wißt ihr, wo        •
• Fingerhut zu Hause?               •
• Tief im Tal von Acherloo          •
• hat er Herd und Klause;           •
• aber schon in jungen Tagen        •
• muß er einen Höcker tragen;       •
• geht er, wunderlicher nie         •
• wallte man auf Erden!             •
• Sitzt er, staunen Kinn und Knie,  •
• daß sie Nachbarn werden.          •
• (...)                             •
• • • • • • • • • • • • • • • • • • •""");
        Item portrait = new Item("Portrait", """
Ein wunderschönes Portrait von Oma vor dem Eifelturm. Ja so fröhlich sah sie immer aus. 
Unterzeichnet wurde es mit 'C.R.W. 11/07/1961'.""");
        Item k5 = new Item("Feuerzeug", """
Ein stinknormales Feuerzeug.""");
        Item k6 = new Item("Marmorfigur", """
Ein aus weißem Marmor geschliffener Siegelstein in Form einer Raute. ◊""");
        Item k7 = new Item("Taschenlampe", """
Eine alte Stabtaschenlampe. Mit der ich früher immer in weite Ferne geleuchtet habe.""");
        Item morse = new Item("Morsealphabet", """
Ein altes Morsealphabet auf Pergament:
A: ● ▬      B: ▬ ● ● ●      C: ▬ ● ▬ ●      D: ▬ ● ●    E: ●
F: ● ● ▬ ●  G: ▬ ▬ ●        H: ● ● ● ●      I: ● ●      J: ● ▬ ▬ ▬
K: ▬ ● ▬    L: ● ▬ ● ●      M: ▬ ▬          N: ▬ ●      O: ▬ ▬ ▬
P: ● ▬ ▬ ●  Q: ▬ ▬ ● ▬      R: ● ▬ ●        S: ● ● ●    T: ▬
U: ● ● ▬    V: ● ● ● ▬      W: ● ▬ ▬        X: ▬ ● ● ▬  Y: ▬ ● ▬ ▬
Z: ▬ ▬ ● ●""");
        Item pflanzenkarte = new Item("Pflanzenkarte", """
Ein Karte aus einer alten Pflanzenkartei.
FINGERHUT
Wissenschaftlicher Name: Digitalis
Familie: Wegerichgewächse (Plantaginaceae)
Ordnung: Lippenblütlerartige (Lamiales)
Die Fingerhüte sind eine Pflanzengattung aus der Familie der Wegerichgewächse. 
Die etwa 25 Arten sind in Europa, Nordafrika und im westlichen Asien verbreitet.
(...)""");
        Item k9 = new Item("Fingerhutsamen", """
Jede dieser Kapseln enthält einige Samen, aus den neue Fingerhüte entspringen können.""");
        Item k10 = new Item("Amethyst", """
Ein wirklich riesiger dunkel violetter Amethyst-Kristall. Wie viel mag der wohl wiegen?""");
        Item chiffre = new Item("Lexikonseite", """
Eine Seite aus einem alten Lexikon.

CÄSAR-CHIFFRE
Die Caesar-Verschlüsselung (auch als Cäsar-Chiffre bezeichnet) ist ein einfaches symmetrisches
Verschlüsselungsverfahren, das auf der monographischen und monoalphabetischen Substitution basiert.
Bei der Verschlüsselung wird jeder Buchstabe des Klartexts auf einen Geheimtextbuchstaben abgebildet. 
Diese Abbildung ergibt sich, indem man die Zeichen eines geordneten Alphabets um eine bestimmte Anzahl 
zyklisch nach rechts verschiebt (rotiert); zyklisch bedeutet, dass man beim Verschieben über Z hinaus 
wieder bei A anfangend weiterzählt. 
Die Anzahl der verschobenen Zeichen bildet den Schlüssel, der für die gesamte Verschlüsselung 
unverändert bleibt. 

Beispiel für eine Verschiebung um drei Zeichen:
A B C D ... W X Y Z
D E F E ... Z A B C""");
        Item k12 = new Item("Fläschchen", """
Ein durchsichtiges Glasfläschchen gefüllt mit einer mysteriösen silber schimmernden Flüssigkeit. 
Auf einem kleinen Etikett steht:
•~ liquida memoria ~•""");

        // INIT ALL LOCATIONS
        Location vorgarten = new Location("Vorgarten", "im",
                """
                        Ein kleiner mit flachen Steinen ausgelegter Weg führt zum Eingang des Hauses. Auf der 
                        linken Seite blühen farbenfrohe Blumen. Rechts vom Weg gibt es einen kleinen Teich, in 
                        dem drei kleine Goldfische schwimmen. Im Süden steht der alte schwarze Audi, mit dem ich 
                        hergefahren bin.""");
        Location flur = new Location("Hausflur", "im",
                """
                        Ein kleiner schmaler Gang bildet den Eingangsbereich des kleinen Hauses.
                        An der Garderobe hängen noch zwei alte Jacken und ein Hut. 
                        Auf dem kleinen Holzschränkchen steht ein Spiegel.""");
        Location schlafzimmer = new Location("Schlafzimmer", "im",
                """
                        Ein gemütlicher kleiner Raum mit einem großen Bett. An der Wand steht ein Kleiderschrank 
                        aus altem Holz.""");
        Location bad = new Location("Badezimmer", "im",
                """
                        Ein vollständig mit weißem Marmor ausgekleideter Raum. Die Verkleidung der Armatur hat 
                        Oma aus bunten Mosaiksteinen selbst gestaltet. Es riecht noch immer leicht nach Lavendel.""");
        Location wohnzimmer = new Location("Wohnzimmer", "im",
                """
                        Ein für dieses Haus eher ungewöhnlich großer Raum. Die alte Schrankwand beinhaltet 
                        allerhand Krimskrams. Neben dem großen gemütlichen Sofa steht Opas alter Plattenspieler.""");
        Location kueche = new Location("Küche", "in der",
                """
                        Ein kleiner fliederfarbener Geschirrschrank steht neben dem alten Gasherd. Auf einem kleinen 
                        Tisch steht ein Vase mit getrockneten Wildblumen. Hier hat Oma immer ihren leckeren 
                        Apfelkuchen gebacken.""");
        Location garten = new Location("Garten", "im",
                """
                        Eine große wild gewachsene Wiese ist von zahlreichen Beeten umgeben. Auf der Fläche verteilt 
                        stehen mehrere Obstbäume, die nach wie vor Früchte tragen. Um den kleinen Tisch stehen vier 
                        Holzstühle. Etwas entfernt bewegt sich die alte Schaukel im Wind.""");
        Location beet = new Location("Beet", "im",
                """
                        Mehrere Beetreihen ziehen sich über die Fläche. Hier haben Oma und Opa ihr leckeres Gemüse 
                        gepflanzt. Es scheint als seien die Pflanzen in den unzähligen Hochbeeten noch immer 
                        quicklebendig. Wer hat sich darum gekümmert? Etwas abseits steht eine Engelsstatue aus Marmor 
                        in einem prächtig blühenden Kreis aus Fingerhut.""");
        Location schuppen = new Location("Schuppen", "im",
                """
                        In dieser morschen Laube lässt sich so gut wie jedes Werkzeug finden. Eine staubige Glühbirne 
                        spendet nur wenig Licht. Es riecht nach Benzin vom alten Rasenmäher.""");
        Location keller = new Location("Keller", "im",
                """
                        Ein ziemlich leerer Kellerraum. Eine einzige kleine Wandleuchte wirft schummriges Licht. Eine 
                        knarrende Holztreppe führt hoch in den Wohnbereich.""");
        Location weinkammer = new Location("Weinkammer", "in der",
                """
                        Der kleine dunkle Raum enthält Regale für einige Hundert Flaschen. Die Weine sind nach Jahrgang 
                        und Weingut sortiert. Es ist kühl und feucht.""");
        Location bib = new Location("Bibliothek", "in der",
                """
                Jede Wand dieses Raumes ist mit Bücherregalen voll gestellt. In der Mitte steht ein kleiner Holztisch 
                und zwei Ledersessel. Ich weiß noch genau, wie Opa mich oft mit hier runter genommen und aus seinen 
                Büchern vorgelesen hat.
                An der Westwand hängt ein Messing-Schild mit der Einprägung '5-L'.""");
        Location fackelraum = new Location("Fackelraum", "im",
                """
                Ein ziemlich kahler Raum aus massiven Steinwänden. An jeder Seite hängt lediglich eine Fackel, die sich 
                beim Betreten des Raumes auf mysteriöse Weise entflammt hat.
                An der Nordwand hängt ein Messing-Schild mit der Einprägung '3-V'.""");
        Location marmorraum = new Location("Marmorhalle", "in der",
                """
                Ein prächtiger mit weißem Marmor ausgekleideter Raum. In der Mitte ragt eine Fischstatue aus einem 
                Marmorbrunnen und speit eine Wasserfontäne.
                An der Ostwand hängt ein Messing-Schild mit der Einprägung '6-A'.""");
        Location dunkelkammer = new Location("Dunkelkammer", "in der",
                """
                Es ist sehr dunkel und ich kann fast nichts erkennen. Nur an der Westwand brennt eine kleine rote Glühbirne.
                An der Nordwand hängt ein Messing-Schild mit der Einprägung '2-P'.""");
        Location gewaechshaus = new Location("Gewächshaus", "im",
                """
                Ein unterirdisches Gewächshaus voller riesiger Pflanzen, die in bunten Farben blühen. In der Mitte wachsen 
                Fingerhüte im Kreis. Über eine große Glaskuppel fällt Licht von draußen hinein.
                An der Südwand hängt ein Messing-Schild mit der Einprägung '4-S'.""");
        Location kristallhoehle = new Location("Kristallhöhle", "in der",
                """
                Das hier muss eine Höhle tief unter dem Haus sein. Ein kleiner Bach rinnt über den Boden. In der Mitte der 
                Höhle steht ein Steinpodest. Ein einziger Sonnenstrahl scheint durch einen Riss in der Decke. 
                Es ist feucht und kalt.
                An der Westwand hängt ein Messing-Schild mit der Einprägung '7-A'.""");
        Location spiegelkabinett = new Location("Spiegelkabinett", "im",
                """
                Ein mysteriöser Raum, der komplett mit Spiegeln ausgekleidet ist. Ich kann mich unendlich oft in ihnen 
                erkennen. Doch ich bin kleiner und sehe viel jünger aus. Habe ich da meinen alten Lieblingspulli an?
                An der Nordwand hängt ein Messing-Schild mit der Einprägung '1-C'""");
        Location himmelssaal = new Location("Himmelssaal", "in einem",
                """
                        Es scheint, als sei ich von Wolken umgeben. Ein schwebender Steinpfad führt zu einem Tor aus Amethyst. 
                        Es ist ein überaus verwunschener Ort.""");
        Location brunnen = new Location("merkwürdigen Ort", "an einem",
                """
                        Wo bin ich hier? Überall nur gleißendes Licht. Vor mir schwebt eine Schale aus Amethyst, in der 
                        sich eine klare Flüssigkeit befindet. Kann ich hier etwas einfüllen?""");

        // INIT ALL GATES
        // closed gates
        Gate g_k1 = new Gate("Haustür", """
                Eine weiß gestrichene Holztür mit einem kleinen Fenster. Auf einem lila Schild steht 'Herzlich Willkommen!'""", k1);
        Gate g_k2 = new Gate("schwere Eisentür", """
                Eine massive alte Tür aus Eisen. Meine Kraft reicht nicht aus sie zu bewegen. Sie scheint zu klemmen.""", k2);
        Gate g_k3 = new Gate("Holztür mit geschnitzten Verzierungen", """
                Eine dunkle Holztür mit verwunschenen Schnitzereien. In der Mitte sitzen acht Walzen mit denen jeweils 
                die Buchstaben von A - Z auswählbar sind. Was mag hier nur das Passwort sein?""", "Acherloo");
        Gate g_k4 = new Gate("Gemälde von Oma", """
                Ein riesiges Portrait von Oma vor dem Eifelturm. Habe ich das nicht auch schon wo anders gesehen? 
                Unter dem Gemälde auf Fußhöhe befindet sich ein Schild mit der Aufschrift 'Unser Tag in Paris' und daneben 
                kann man acht Zahlen in ein Nummernpad eintippen.""", "11071961");
        Gate g_k5 = new Gate("Steinwand", """
                In der rechten Ecke der Westwand kann ich einen kleinen Schlitz erkennen. Wenn ich dagegen klopfe, hört sie 
                sich irgendwie anders an als die übrigen Wände. Außerdem guckt knapp unter der Fackel eine Art Docht heraus. 
                Es scheint, als hätte er schonmal gebrannt.""", k5);
        Gate g_k6 = new Gate("Marmorwand", """
                Eine ebenso prächtige Wand aus Marmor. In der Mitte befindet sich ein kleine Vertiefung in Form einer Raute. 
                ◊""", k6);
        Gate g_k7 = new Gate("Steinwand", """
In Mitten der Wand, knapp unter der Glühbirne hängt ein kleines Schild. Durch das minimale 
Licht kann man die Aufschrift gerade so lesen:
Bring Licht ins Dunkle!
Dadrunter befindet sich ein kleines dunkles Loch.""", k7);
        Gate g_k8 = new Gate("Holztür", """
Die Tür aus dunklem Nußholz ist hinter einer großen Monstera Pflanze versteckt. In das Holz 
sind merkwürdige Zeichen eingraviert:
▬ ● ●   ● ●   ▬ ▬ ●   ● ●   ▬   ● ▬   ● ▬ ● ●   ● ●   ● ● ●
Neben dem Türknauf befindet sich eine kleine Tastatur.""", "digitalis");
        Gate g_k9 = new Gate("Spiegeltür", """
Eine Tür mit einem großen Spiegel. In der Mitte befindet sich ein kleines Loch. 
Dadrüber wurde ein prächtiger Fingerhut gemalt. Es sieht so aus, als würde er 
aus dem Loch herauswachsen. Wie kann ich diese Tür nur öffnen? 
Vielleicht muss ich etwas in das Loch werfen.""", k9);
        Gate g_k10 = new Gate("Marmortor", """
Dieses Tor ist der Eingang in den riesigen mysteriösen Marmorblock, der schon seit jeher 
im Garten von Oma und Opa stand. Ich habe mich schon immer gefragt, was es wohl damit auf 
sich hat. 
In Mitten des Tores befindet sich eine Pendelwaage mit der Aufschrift:
~~ Der wohl Größte seiner Art. ~~""", k10);
        Gate g_k11 = new Gate("Tor aus Amethyst", """
Ein riesiges amethystfarbenes Tor. Darin eingraviert steht in großen Buchstaben: 
•| ALLE ZUSAMMEN, VERSCHOBEN UM 7 |•
Etwas sagt mir, dass ich ein Wort mit den Fingern auf das Tor zeichnen soll. 
Doch um welches Wort handelt es sich?""");

        // open gates
        Gate g_01 = new Gate("Tür zwischen Flur und Schlafzimmer", "Eine einfache weiße Holztür.");
        Gate g_02 = new Gate("Tür zwischen Schlaf- und Badezimmer", "Eine schöne fliederfarbene Holztür.");
        Gate g_03 = new Gate("Tür zwischen Flur und Wohnzimmer", "Eine weiße mit Stuck verzierte Holztür. In der oberen Hälfte sitzt ein kleines Fenster.");
        Gate g_04 = new Gate("Tür zwischen Bade- und Wohnzimmer", "Eine einfache weiße Holztür.");
        Gate g_05 = new Gate("Tür zwischen Wohnzimmer und Küche", "Eine einfache weiße Holztür. In der oberen Hälfte sitzt ein Fenster.");
        Gate g_06 = new Gate("Gartentür", "Die Hintertür zwischen Wohnzimmer und Garten. Der weiße Lack bröckelt an einigen Stellen.");
        Gate g_07 = new Gate("Weg zwischen Gartenwiese und Beet", "Ein einfacher Weg aus flachen Steinen.");
        Gate g_08 = new Gate("Schuppentür", "Eine alte morsche Holztür, die beim Öffnen und Schließen quietscht.");
        Gate g_09 = new Gate("Kellertür", "Eine alte Metalltür, zwischen Keller und Weinkammer.");

        // SET UP ALL CONNECTIONS BETWEEN LOCATIONS AND GATES
        // every gate should appear twice, so the assignment automation for the gate endings will work correctly.
        vorgarten.setGates(g_k1, null, null, null);
        flur.setGates(g_03, g_k2, g_k1, g_01);
        schlafzimmer.setGates(g_02, g_01, null, null);
        bad.setGates(null, g_04, g_02, null);
        wohnzimmer.setGates(g_06, g_05, g_03, g_04);
        kueche.setGates(null, null, null, g_05);
        garten.setGates(g_k10, g_08, g_06, g_07);
        beet.setGates(null, null, null, g_07);
        schuppen.setGates(null, g_08, null, null);
        keller.setGates(g_k3, g_09, null, g_k2);
        weinkammer.setGates(null, null, null, g_09);
        bib.setGates(g_k4, null, g_k3, null);
        fackelraum.setGates(null, g_k5, g_k4, null);
        marmorraum.setGates(g_k6, null, null, g_k5);
        dunkelkammer.setGates(null, null, g_k6, g_k7);
        gewaechshaus.setGates(g_k9, g_k7, null, g_k8);
        kristallhoehle.setGates(null, g_k8, null, null);
        spiegelkabinett.setGates(null, null, g_k9, null);
        himmelssaal.setGates(g_k11, null, g_k10, null);
        brunnen.setGates(null, null, g_k11, null);


        // add items to locations
        schlafzimmer.inventory().addItem(portrait);
        wohnzimmer.inventory().addItem(gedicht);
        kueche.inventory().addItem(k5);
        beet.inventory().addItem(k9);
        beet.inventory().addItem(k6);
        schuppen.inventory().addItem(k7);
        schuppen.inventory().addItem(k2);
        bib.inventory().addItem(morse);
        bib.inventory().addItem(pflanzenkarte);
        bib.inventory().addItem(chiffre);
        kristallhoehle.inventory().addItem(k10);
        spiegelkabinett.inventory().addItem(k12);

        // set final objects
        // (player has to be in the finalLocation and then use the finalKey)
        finalLocation = brunnen;
        finalKey = k12;

        // init the player
        this.player = new Player(vorgarten);
        // add some items to players inventory
        player.inventory().addItem(k1);
        player.inventory().addItem(brief);

        // set game states
        finished = false;
        solved = false;
    }

    /**
     *  Basic game logic.
     */
    public void run() {

        // initial messages
        this.printInitialMessages();

        // run until game is finished
        while (!this.finished) {
            // until game is solved
            if(!this.solved){
                // get current handler and process game logic
                currentHandler = this.getCurrentHandler(); // must be the first operation before handle() & printUpdate()
                this.processGameLogic();
            }
            else {
                this.printGameIsSolvedMessage();
                this.finish();
            }
        }
    }

    /**
     * Processes the game logic: it executes the handle function of the current handler.
     */
    private void processGameLogic() {
        // for debugging
//        this.handleCount++;
//        this.handleTypes += currentHandler.getType() + " ";

        // execute the handler actions
        currentHandler.handle();

        // update the terminal
        this.printHandlerMessage();

        // debug
//        System.out.println("actual Gate: " + (this.player().getActualGate() != null ? this.player().getActualGate().getName() : "no gate selected"));
//        System.out.println("keyword: " + (this.player().getActualGate().hasKeyword() ? this.player().getActualGate().getKeyword() : "no"));

        // ask for next input
        if(!currentHandler.getType().equalsIgnoreCase("error") && !currentHandler.getType().equalsIgnoreCase("exit") && !this.solved) // but don't ask for an ERROR or EXIT command
            System.out.println(inputMsg);
    }

    private void printInitialMessages(){
        // Game headline
        System.out.println(this.gameHeadline);

        // Introductions
        System.out.println(String.format(this.introMsg, this.player.getPosition().getName()));

        // TEST OUTRO
//        System.out.println(this.solvedGameMsg);

        // first terminal outputs as overview
        System.out.println(this.player.getPosition().getDescription());
        System.out.println(inputMsg);
    }

    private void printGameIsSolvedMessage(){
        System.out.println(this.solvedGameMsg);
    }

    /**
     * Returns the current handler if it matches the user input.
     * @return The current {@code Handler} if the given user input matches any handler command, else an {@code ErrorHandler}.
     */
    private Handler getCurrentHandler() {
        // ask for and store the given input
        this.lineInput = this.reader.readLine(inputCall).trim();

        // parse command into parts
        this.commandParts = this.lineInput.split(" ", 2);

        // check if user input matches a valid command for a handler
        for (Handler handler : this.handlers) {
            if (handler.matches(this.getInputCommand())) {
                // if it's a match return this handler
                return handler;
            }
        }
        // else return default error handler
        return errorHandler;
    }

    /**
     * Method to print the reaction to the user.
     */
    private void printHandlerMessage(){
        // just for debugging
//        System.out.println("[" + this.handleCount + "] " + this.handleTypes);

        // print Handler message
        System.out.println("\n" + currentHandler.getMessage());
    }

    /**
     * Get the full user input.
     * @return Full user input from terminal.
     */
    public String getLineInput(){
        return this.lineInput;
    }

    /**
     * Get the full user answer to the given terminal message.
     * @param terminalMsg Message the user should see.
     * @return Full {@code String} that holds the answer to the given message.
     */
    public String getLineInput(String terminalMsg){
        this.lineInput = this.reader.readLine(terminalMsg).trim();
        return this.lineInput;
    }

    /**
     * Get only the command part of the user input.
     * @return {@code String} that holds the actual command from the user input.
     */
    public String getInputCommand(){
        return this.commandParts[0];
    }

    /**
     * Get only the command part of the user input as combined {@code String}.
     * @return {@code String} that holds the actual command from the user input.
     */
    public String getInputObject(){
        if(this.commandParts.length > 1)
            return this.commandParts[1];
        else
            return "";
    }

    /**
     * Finish the game.
     */
    public void finish(){
        this.finished = true;
    }

    /**
     * Solve the game.
     */
    public void solve(){
        this.solved = true;
    }

    /**
     * Returns the player of the Game instance.
     * @return {@code Player} of the game.
     */
    public Player player(){
        return this.player;
    }

    /**
     * Returns the final item of the game.
     * @return {@code Item}
     */
    public Location finalLocation() {
        return finalLocation;
    }

    /**
     * Returns the final key of the game.
     * @return {@code Item}
     */
    public Item finalKey() {
        return finalKey;
    }
}