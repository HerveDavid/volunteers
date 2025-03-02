import java.util.*;


public class Merge {
    public final String key;
    private ArrayList<User> users;
    private ArrayList<User> result;

    public Merge(String key){
        this.key = key;
        this.users = new ArrayList<>();
        this.result = new ArrayList<>();
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void mergeUsers(){
        User user = new User(key, getValue(Header.FIRSTNAME), getValue(Header.USERNAME), getValue(Header.EMAIL), getValue(Header.PHONE));
        this.result.add(user);
//
//        HashMap<String, User> dict = new HashMap<>();
//
//        User buffUser = users.get(0);
//        dict.put(buffUser.firstname.toUpperCase(Locale.ROOT), buffUser);
//
//        for (int i = 1; i < users.size(); i++) {
//            String key = users.get(i).firstname.toUpperCase(Locale.ROOT);
//            int ld = Levenshtein.ld(key, buffUser.username.toUpperCase(Locale.ROOT));
//            if(ld <= 1){
//            }else{
//                dict.put(key, users.get(i));
//            }
//            buffUser = users.get(i);
//        }
//
//        for(User user: dict.values()){
//            this.result.add(user);
//        }
    }

    public ArrayList<User> getResult() {
        return result;
    }

    private HashMap<String, Double> getPercentageByValue(Header value){
        HashMap<String, Double> dict = new HashMap<>();
        for(User user: users){
            String valueInUsers = "";
            switch (value){
                case LASTNAME:
                    valueInUsers = user.lastname;
                    break;
                case FIRSTNAME:
                    valueInUsers = user.firstname;
                    break;
                case USERNAME:
                    valueInUsers = user.username;
                    break;
                case EMAIL:
                    valueInUsers = user.email;
                    break;
                case PHONE:
                    valueInUsers = user.phone;
                    break;
            }
            if(!valueInUsers.isEmpty()){
                if(dict.containsKey(valueInUsers)){
                    dict.put(valueInUsers, dict.get(valueInUsers) + 1.);
                }else {
                    dict.put(valueInUsers, 1.);
                }
            }
        }
        for(String key: dict.keySet()){
            dict.put(key, dict.get(key) / users.size());
        }
        return dict;
    }

    private String getValue(Header field){
        String firstname = "";
        Double max = 0.;
        for (Map.Entry<String, Double> entry : getPercentageByValue(field).entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            if(max < value){
                max = value;
                firstname = key;
            }
        }
        return firstname;
    }

}
