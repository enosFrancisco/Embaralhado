package estagio3.ufpb.com.br.embaralhando;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeferson on 20/11/2016.
 */

public class DataBase {
    private SQLiteDatabase bd;

    public DataBase(android.content.Context context){
        DataBaseCore auxBd = new DataBaseCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void insertWord(Word word){
        ContentValues valores = new ContentValues();
        valores.put("name", word.getName());
        valores.put("image", word.getImageBytes());
        valores.put("context", word.getContext());

        bd.insert("words", null, valores);
    }
    public void insertContext(Context context){
        ContentValues valores = new ContentValues();
        valores.put("name", context.getName());
        valores.put("image", context.getImageBytes());

        bd.insert("contexts", null, valores);
    }
    public void insertScore(Score score){
        ContentValues valores = new ContentValues();
        valores.put("score", score.getScore());
        valores.put("image", score.getImageScoreBytes());

        bd.insert("scores", null, valores);
    }


    public void updateWord(Word word){ //implementation Future :)
        ContentValues valores = new ContentValues();
        valores.put("name", word.getName());
        valores.put("image", word.getImageBytes());

        bd.update("words", valores, "_id = ?", new String[]{""+ word.getId()});
    }


    public void deleteWord(Word word){
        bd.delete("words", "_id = ?"+ word.getId(), null);
    }

    public void deleteContext(Context context){
        bd.delete("words","context = ?",new String[]{ context.getName()});
        bd.delete("contexts", "_id = "+ context.getId(), null);
    }

    public void deleteScore(Score score){
        bd.delete("scores", "_id = "+score.getId(), null);
    }


    public List<Word> searchWordsDatabase(String nameContext){
        final List<Word> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image", "context"};

        final Cursor cursor = bd.query("words", columns, "context = ?", new String[]{"" + nameContext}, null, null, "name ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                byte [] image = cursor.getBlob(2);
                String context = cursor.getString(3);
                Word w = new Word(id,image,name,context);
                list.add(w);

            }while(cursor.moveToNext());
        }

        return(list);
    }
    public List<Context> searchMyContextsDatabase(){
        List<Context> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "name", "image"};

        Cursor cursor = bd.query("contexts", columns, null, null, null, null, "name ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                byte [] image = cursor.getBlob(2);
                Context w = new Context(id,image,name);
                list.add(w);

            }while(cursor.moveToNext());
        }

        return(list);
    }

    public List<Score> searchScoresDatabase(){
        List<Score> list = new ArrayList<>();
        String[] columns = new String[]{"_id", "score", "image"};

        Cursor cursor = bd.query("scores", columns, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            do{

                long id = cursor.getLong(0);
                int score = cursor.getInt(1);
                byte [] image = cursor.getBlob(2);
                Score s = new Score(id,image,score);
                list.add(s);

            }while(cursor.moveToNext());
        }

        return(list);
    }
}
