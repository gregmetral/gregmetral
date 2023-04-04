using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class brick : MonoBehaviour
{
    [SerializeField] protected GameObject ref_jesus;
    
    // Start is called before the first frame update
    void Start()
    {
        GetComponent<SpriteRenderer>().material.color = new Color(Random.Range(0, 255), Random.Range(0, 255), Random.Range(0, 255));
    }

    // Update is called once per frame
    void Update()
    {

    }

    public void OnCollisionEnter2D(Collision2D collision)
    {
        int r = Random.Range(0, 8);
        float posX = transform.position.x;
        float posY = transform.position.y;
        Destroy(gameObject);
        spawner.brick_count--;
        if (r == 0)
        {
            GameObject newjesus = Instantiate(ref_jesus);
            newjesus.transform.position = new Vector3(posX, posY, 0);
        }
    }




}
