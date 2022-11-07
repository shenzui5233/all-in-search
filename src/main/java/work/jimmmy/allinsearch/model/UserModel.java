package work.jimmmy.allinsearch.model;

import java.util.Date;

public class UserModel {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.created_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.updated_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private Date updatedAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.telphone
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private String telphone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.nickname
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private String nickname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.gender
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    private Integer gender;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.created_at
     *
     * @return the value of user.created_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.created_at
     *
     * @param createdAt the value for user.created_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.updated_at
     *
     * @return the value of user.updated_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.updated_at
     *
     * @param updatedAt the value for user.updated_at
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.telphone
     *
     * @return the value of user.telphone
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.telphone
     *
     * @param telphone the value for user.telphone
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.nickname
     *
     * @return the value of user.nickname
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.nickname
     *
     * @param nickname the value for user.nickname
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.gender
     *
     * @return the value of user.gender
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.gender
     *
     * @param gender the value for user.gender
     *
     * @mbg.generated Mon Nov 07 02:01:23 CST 2022
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }
}