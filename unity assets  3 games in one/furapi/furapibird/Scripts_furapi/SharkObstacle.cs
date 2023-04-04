using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SharkObstacle : MonoBehaviour
{
    const float sharkSpeed = 9f;
    const float despawn_posX = -12f;

    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        transform.Translate(-sharkSpeed * Time.deltaTime, 0, 0);
        if (transform.position.x < despawn_posX || bird.restart == true)
        {
            Destroy(gameObject);
        }
    }
}