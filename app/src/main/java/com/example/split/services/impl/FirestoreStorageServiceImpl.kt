package com.example.split.services.impl

import android.util.Log
import com.example.split.data.Expense
import com.example.split.data.Group
import com.example.split.data.User
import com.example.split.services.AccountService
import com.example.split.services.StorageService
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "FirestoreStorageServiceImpl"

class FirestoreStorageServiceImpl @Inject constructor(
    private val auth: AccountService,
    private val firestore: FirebaseFirestore,
) : StorageService {
    private val groupsRef: CollectionReference = firestore.collection("groups")
    private val usersRef: CollectionReference = firestore.collection("users")

    override suspend fun addUser(id: String, user: User) {
        usersRef.document("1").set(user)
    }

    override suspend fun getUserById(id: String): User? {
        if (id == "") {
            Log.e("StorageService", "Error: ID can't be empty when retrieving User")
            return null
        }
        val result = usersRef.document(id).get().await()
        return result.toObject<User>()?.copy(id = id)
    }

    override suspend fun getUserByFriendCode(code: String): User? {
        val users = usersRef
            .whereEqualTo("friendCode", code)
            .get()
            .await()
        return if (users.isEmpty) null else users.first().toObject<User>().copy(id = users.first().id)
    }

    override suspend fun addGroup(group: Group) {
        groupsRef.document().set(group)
    }

    override suspend fun addExpenseToGroup(
        expense: Expense,
        groupId: String
    ) {
        groupsRef.document(groupId).collection("expenses").document().set(expense)
    }

    override suspend fun getGroupsByUserId(id: String): List<Group> {
        val groups = groupsRef
            .whereGreaterThan("memberCount", 2)
            .whereArrayContains("members", id)
            .get()
            .await()
        return groups.toObjects<Group>()
    }

    override suspend fun getGroup(id: String): Group? {
        val group = groupsRef.document(id).get().await()
        return group.toObject<Group>()?.copy(id = id)
    }

    override fun getGroupsFlow(id: String, isFriend: Boolean): Flow<List<Group>> = callbackFlow {
        val ref = if (!isFriend) groupsRef.whereGreaterThan("memberCount", 2) else groupsRef.whereLessThanOrEqualTo("memberCount", 2)
        val registration = ref.whereArrayContains("members", id).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                close(e)
                return@addSnapshotListener
            }

            val groups = snapshot?.documents?.map { doc ->
                val group = doc.toObject(Group::class.java)!!
                group.copy(id = doc.id)
            } ?: emptyList()
            trySend(groups)
        }
        awaitClose { registration.remove() }
    }


    override suspend fun getFriendsByUserId(id: String): List<Group> {
        val groups = groupsRef
            .whereEqualTo("memberCount", 2)
            .whereArrayContains("members", id)
            .get()
            .await()
        return groups.toObjects<Group>()
    }

    override fun getExpensesFlow(groupId: String): Flow<List<Expense>> = callbackFlow {
        val registration =
            groupsRef.document(groupId).collection("expenses").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    close(e)
                    return@addSnapshotListener
                }

                val expenses = snapshot?.toObjects(Expense::class.java) ?: emptyList()
                trySend(expenses)
            }
        awaitClose { registration.remove() }
    }

    override suspend fun getExpensesCashed(groupId: String): List<Expense> {
        return try {
            val snapshot = firestore
                .collection("groups")
                .document(groupId)
                .collection("expenses")
                .orderBy("date", Query.Direction.DESCENDING)
                .get(Source.CACHE)
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Expense::class.java)
            }
        } catch (e: Exception) {
            // Return empty list if cache miss
            emptyList()
        }
    }

    override suspend fun deleteExpense(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(expense: Expense) {
        TODO("Not yet implemented")
    }

}