package com.example.final_inf_202;

import java.util.ArrayList;
import java.util.List;

public interface UserProfileComponent {
    void display();
}
class ProfileLeaf implements UserProfileComponent {
    private String label;
    private String value;

    public ProfileLeaf(String label, String value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public void display() {
        System.out.println(label + ": " + value);
    }

    public String getLabel() {
        return label;
    }
    public String getValue() {
        return value;
    }
}
class UserProfileComposite implements UserProfileComponent {
    private List<UserProfileComponent> components = new ArrayList<>();
    private String label;

    public UserProfileComposite(String label) {
        this.label = label;
    }

    public void addComponent(UserProfileComponent component) {
        components.add(component);
    }

    @Override
    public void display() {
        System.out.println(label);
        for (UserProfileComponent component : components) {
            component.display();
        }
    }
}

class UserProfile implements UserProfileComponent {

    private List<UserProfileComponent> components = new ArrayList<>();

    public void addComponent(UserProfileComponent component) {
        components.add(component);
    }

    public UserProfileComponent getComponent(String label) {
        for (UserProfileComponent component : components) {
            if (component instanceof ProfileLeaf && ((ProfileLeaf) component).getLabel().equals(label)) {
                return component;
            }
        }
        return null;
    }

    @Override
    public void display() {
        for (UserProfileComponent component : components) {
            component.display();
        }
    }

    public static void main(String[] args) {
        UserProfile userProfile = new UserProfile();
        userProfile.display();
    }
}

