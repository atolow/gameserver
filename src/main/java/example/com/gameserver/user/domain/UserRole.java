package example.com.gameserver.user.domain;

public enum UserRole {
    ADMIN, NEWBIE;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}