package com.script972.clutchclient.helpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which provide the help of the getting of the Android contact information
 */

public final class ContactHelper {

    /**
     * Interface which provide the contact callback
     */
    public interface OnContactCallback {

        /**
         * Method which provide the start getting of the contacts
         */
        void onStart();

        /**
         * Method which provide the on result functional
         *
         * @param isSuccess {@link Boolean} value if it success
         * @param exception instance of the {@link Exception}
         */
        void onResult(boolean isSuccess,
                      @Nullable Exception exception,
                      @NonNull List<ContactContainer> contacts);
    }

    /**
     * Class which provide the task for the getting contact
     */
    private static class ContactTask extends AsyncTask<Void, Void, List<ContactContainer>> {

        /**
         * Instance of the {@link WeakReference}
         */
        private final WeakReference<Context> contextReference;

        /**
         * Instance of the {@link WeakReference}
         */
        private final WeakReference<OnContactCallback> callbackReference;

        /**
         * Constructor which provide the create task with parameters
         *
         * @param context  instance of the {@link Context}
         * @param callback instance of the {@link OnContactCallback}
         */
        private ContactTask(@NonNull Context context,
                            @NonNull OnContactCallback callback) {
            this.contextReference = new WeakReference<Context>(context);
            this.callbackReference = new WeakReference<OnContactCallback>(callback);
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         */
        @Override
        protected void onPreExecute() {
            final OnContactCallback callback = this.callbackReference.get();
            if (callback != null) {
                callback.onStart();
            }
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param voids The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<ContactContainer> doInBackground(Void... voids) {
            final List<ContactContainer> contacts = new ArrayList<>();
            final Context context = this.contextReference.get();
            if (context == null) {
                return contacts;
            }
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cursor != null ? cursor.getCount() : 0) > 0) {
                while (cursor != null && cursor.moveToNext()) {
                    long id = cursor.getLong(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                            id);
                    Uri photoUri = Uri.withAppendedPath(contactUri,
                            ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                    String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    String photo = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.PHOTO_URI));
                    String photo1 = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    if (cursor.getInt(cursor.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor phoneCursor = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{String.valueOf(id)}, null);
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contacts.add(new ContactContainer(name, phoneNumber, photo, photoUri));
                        }
                        phoneCursor.close();
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return contacts;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         */
        @Override
        protected void onPostExecute(List<ContactContainer> contacts) {
            final OnContactCallback callback = this.callbackReference.get();
            if (callback != null) {
                if (contacts.size() > 0) {
                    callback.onResult(true,
                            null,
                            contacts);
                } else {
                    callback.onResult(false,
                            new Exception("Contact list is empty"),
                            contacts);
                }
            }
        }
    }

    /**
     * Class which provide the contact container
     */
    public static final class ContactContainer {

        /**
         * Field value from {@link ContactContainer}
         */
        @Nullable
        public final String name;

        /**
         * Field value from {@link ContactContainer}
         */
        @Nullable
        public final String phone;

        /**
         * Field value from {@link ContactContainer}
         */
        @Nullable
        public final String photo;

        /**
         * Field value from {@link ContactContainer}
         */
        @Nullable
        public final Uri thumbnail;

        /**
         * Constructor which provide the create the {@link ContactContainer} with parameters
         *
         * @param name      {@link String} value of the name
         * @param phone     {@link String} value of the phone
         * @param photo     {@link String} value of the photo
         * @param thumbnail {@link Uri} value of the thumbnail
         */
        public ContactContainer(@Nullable String name,
                                @Nullable String phone,
                                @Nullable String photo,
                                @Nullable Uri thumbnail) {
            this.name = name;
            this.phone = phone;
            this.photo = photo;
            this.thumbnail = thumbnail;
        }
    }

    /**
     * Method which provide the getting contact
     *
     * @param context  instance of the {@link Context}
     * @param callback instance of the {@link OnContactCallback}
     */
    public static void getContacts(@Nullable Context context,
                                   @Nullable OnContactCallback callback) {
        if ((context == null)) {
            if (callback != null) {
                callback.onResult(false, new Exception("Context is null"),
                        new ArrayList<ContactContainer>());
            }
            return;
        }

        if (callback == null) {
            return;
        }
        new ContactTask(context, callback).execute();
    }

}
