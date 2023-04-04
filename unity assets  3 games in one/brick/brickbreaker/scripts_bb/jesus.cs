using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class jesus : MonoBehaviour
{
    

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {

    }
    public void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.tag == "bricktag" || collision.gameObject.tag == "jesustag")
        {
            Destroy(gameObject);
        }
        if (collision.gameObject.tag == "balltag" || collision.gameObject.tag == "padlletag")
        {
            
            Destroy(gameObject);
            ball.score += 500;
            
        }
    }


}
